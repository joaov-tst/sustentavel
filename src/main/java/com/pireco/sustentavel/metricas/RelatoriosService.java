package com.pireco.sustentavel.metricas;

import com.pireco.sustentavel.material.Material;
import com.pireco.sustentavel.material.MaterialRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelatoriosService {

    private final MaterialRepository materialRepository;
    private final CalculoMetricasService calculoMetricasService;

    public RelatoriosService(MaterialRepository materialRepository,
                             CalculoMetricasService calculoMetricasService) {
        this.materialRepository = materialRepository;
        this.calculoMetricasService = calculoMetricasService;
    }

    public RelatorioDesempenhoResponse gerarRelatorio(RelatorioFiltroRequest filtro) {

        LocalDate inicio = filtro.inicio();
        LocalDate fim = filtro.fim();
        String categoria = filtro.categoria();
        String tipoPeriodo = filtro.tipoPeriodo() == null ? "DIA" : filtro.tipoPeriodo();

        // 1) Buscar todos materiais e filtrar por data + categoria
        List<Material> materiais = materialRepository.findAll();

        List<Material> filtrados = materiais.stream()
                .filter(m -> m.getCriadoEm() != null)
                .filter(m -> {
                    LocalDate data = m.getCriadoEm().toLocalDate();
                    return (!data.isBefore(inicio) && !data.isAfter(fim));
                })
                .filter(m -> categoria == null
                        || categoria.isBlank()
                        || categoria.equalsIgnoreCase("Todos os Materiais")
                        || m.getNome().equalsIgnoreCase(categoria))
                .toList();

        long registrosNoPeriodo = filtrados.size();

        // 2) Agrupar por dia/mês/ano e calcular métricas por período
        List<PeriodoMetricasDTO> periodos = agruparPorPeriodoComMetricas(filtrados, tipoPeriodo);

        // 3) Distribuição por categoria (para gráfico de barras/pizza)
        Map<String, Double> mapa = new HashMap<>();
        for (Material m : filtrados) {
            if (m.getQuantidade() != null) {
                mapa.merge(m.getNome(), m.getQuantidade(), Double::sum);
            }
        }

        List<DistribuicaoMaterialDTO> distribuicaoPorCategoria = mapa.entrySet().stream()
                .map(e -> new DistribuicaoMaterialDTO(e.getKey(), e.getValue()))
                .toList();

        return new RelatorioDesempenhoResponse(
                periodos,
                distribuicaoPorCategoria,
                registrosNoPeriodo
        );
    }

    private List<PeriodoMetricasDTO> agruparPorPeriodoComMetricas(List<Material> materiais,
                                                                  String tipoPeriodo) {

        Map<String, List<Material>> agrupado = new HashMap<>();

        for (Material m : materiais) {
            LocalDate data = m.getCriadoEm().toLocalDate();
            String chave;

            switch (tipoPeriodo.toUpperCase()) {
                case "MES" -> chave = String.format("%02d/%d", data.getMonthValue(), data.getYear());
                case "ANO" -> chave = String.valueOf(data.getYear());
                default -> chave = String.format("%02d/%02d", data.getDayOfMonth(), data.getMonthValue());
            }

            agrupado.computeIfAbsent(chave, k -> new ArrayList<>()).add(m);
        }

        List<PeriodoMetricasDTO> resultado = new ArrayList<>();

        for (Map.Entry<String, List<Material>> entry : agrupado.entrySet()) {
            String label = entry.getKey();
            List<Material> lista = entry.getValue();

            double totalReaproveitado = lista.stream()
                    .map(Material::getQuantidade)
                    .filter(Objects::nonNull)
                    .mapToDouble(Double::doubleValue)
                    .sum();

            // usa o cálculo geral, mas só para essa lista
            ResultadoMetricas rm = calculoMetricasService.calcularParaLista(lista);

            resultado.add(new PeriodoMetricasDTO(
                    label,
                    rm.residuo().doubleValue(),
                    rm.co2().doubleValue(),
                    rm.agua().doubleValue(),
                    rm.economiaDescarte().doubleValue(),
                    rm.economiaSubstituicao().doubleValue(),
                    totalReaproveitado,
                    rm.economiaTotal().doubleValue()
            ));
        }

        // ordenar pelo rótulo
        return resultado.stream()
                .sorted(Comparator.comparing(PeriodoMetricasDTO::periodo))
                .collect(Collectors.toList());
    }
}
