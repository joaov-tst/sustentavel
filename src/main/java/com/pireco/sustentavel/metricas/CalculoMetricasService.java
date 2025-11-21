package com.pireco.sustentavel.metricas;

import com.pireco.sustentavel.material.Material;
import com.pireco.sustentavel.material.MaterialRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.pireco.sustentavel.usuario.UsuarioRepository;
import com.pireco.sustentavel.usuario.TipoUsuarioEnum;


@Service
public class CalculoMetricasService {

    private final MaterialRepository materialRepository;
    private final FatorConversaoRepository fatorConversaoRepository;
    private final FatorFinanceiroRepository fatorFinanceiroRepository;
    private final UsuarioRepository usuarioRepository;

    public CalculoMetricasService(MaterialRepository materialRepository,
                                  FatorConversaoRepository fatorConversaoRepository,
                                  FatorFinanceiroRepository fatorFinanceiroRepository,
                                  UsuarioRepository usuarioRepository) {

        this.materialRepository = materialRepository;
        this.fatorConversaoRepository = fatorConversaoRepository;
        this.fatorFinanceiroRepository = fatorFinanceiroRepository;
        this.usuarioRepository = usuarioRepository; // 👈 AGORA está inicializado!
    }

    // ================== MÉTRICAS GERAIS (O QUE JÁ EXISTIA) ==================
    public ResultadoMetricas calcular() {
        List<Material> materiais = materialRepository.findAll();
        return calcularParaLista(materiais);
    }

    // ================== NOVO: LISTA DE MÉTRICAS POR MATERIAL ==================
    public List<ResumoMetricasMaterial> calcularPorMaterial() {
        List<Material> materiais = materialRepository.findAll();
        List<ResumoMetricasMaterial> resumo = new ArrayList<>();

        for (Material m : materiais) {
            double qtdDouble = m.getQuantidade() == null ? 0.0 : m.getQuantidade();
            BigDecimal quantidade = BigDecimal.valueOf(qtdDouble);

            if (quantidade.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            BigDecimal residuo = quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.RESIDUO));
            BigDecimal co2 = quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.CO2));
            BigDecimal agua = quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.AGUA));
            BigDecimal energia = quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.ENERGIA));

            var finOpt = fatorFinanceiroRepository.findByMaterialId(m.getId());

            BigDecimal economiaDescarte = zero();
            BigDecimal economiaSubstituicao = zero();

            if (finOpt.isPresent()) {
                var f = finOpt.get();
                if (f.getEconomiaDescarte() != null)
                    economiaDescarte = quantidade.multiply(f.getEconomiaDescarte());
                if (f.getEconomiaSubstituicao() != null)
                    economiaSubstituicao = quantidade.multiply(f.getEconomiaSubstituicao());
            }

            BigDecimal economiaTotal = economiaDescarte.add(economiaSubstituicao);

            resumo.add(new ResumoMetricasMaterial(
                    m.getNome(),
                    quantidade,
                    m.getUnidade(),
                    arred(residuo),
                    arred(co2),
                    arred(agua),
                    arred(energia),
                    arred(economiaDescarte),
                    arred(economiaSubstituicao),
                    arred(economiaTotal)
            ));
        }

        return resumo;
    }

    // ================== AUXILIARES ==================
    private BigDecimal fator(Material material, FatorConversao.TipoMetrica tipo) {
        return fatorConversaoRepository
                .findByMaterialIdAndTipo(material.getId(), tipo)
                .map(FatorConversao::getFator)
                .orElse(zero());
    }

    private BigDecimal zero() {
        return BigDecimal.ZERO;
    }

    private BigDecimal arred(BigDecimal valor) {
        return valor.setScale(4, RoundingMode.HALF_UP);
}





    // ================== NOVO: MÉTRICAS PARA A HOME PÚBLICA ==================
    public HomeMetricasResponse calcularHomeMetricas() {
        // 1) total reaproveitado = soma das quantidades dos materiais
        List<Material> materiais = materialRepository.findAll();

        double totalReaproveitado = materiais.stream()
                .map(Material::getQuantidade)
                .filter(q -> q != null)
                .mapToDouble(Double::doubleValue)
                .sum();

        // 2) usa o cálculo geral que você já fez
        ResultadoMetricas resultado = calcular();

        // economia total em double
        double economiaGerada = resultado.economiaTotal().doubleValue();

        // 3) distribuição por material: agrupa por nome e soma quantidades
        java.util.Map<String, Double> mapaPorMaterial = new java.util.HashMap<>();

        for (Material m : materiais) {
            Double qtd = m.getQuantidade();
            if (qtd == null) continue;

            mapaPorMaterial.merge(m.getNome(), qtd, Double::sum);
        }

        List<DistribuicaoMaterialDTO> distribuicaoPorMaterial = mapaPorMaterial.entrySet()
                .stream()
                .map(e -> new DistribuicaoMaterialDTO(e.getKey(), e.getValue()))
                .toList();

        // 4) impacto ambiental – reaproveita os valores já calculados
        ImpactoAmbientalDTO impactoAmbiental = new ImpactoAmbientalDTO(
                resultado.residuo().intValue(),
                resultado.agua().doubleValue(),
                resultado.co2().doubleValue()
        );

        // 5) clientes ativos e pontos distribuídos
        long clientesAtivos = usuarioRepository.countByTipoUsuario(TipoUsuarioEnum.Cliente);
        long pontosDistribuidos = 0L; // ainda fixo por enquanto

        return new HomeMetricasResponse(
                totalReaproveitado,
                economiaGerada,
                clientesAtivos,
                pontosDistribuidos,
                distribuicaoPorMaterial,
                impactoAmbiental
        );
    }

    // novo: calcula as métricas para uma lista específica de materiais
    public ResultadoMetricas calcularParaLista(List<Material> materiais) {

        BigDecimal residuo = zero();
        BigDecimal co2 = zero();
        BigDecimal agua = zero();
        BigDecimal energia = zero();
        BigDecimal economiaDescarte = zero();
        BigDecimal economiaSubstituicao = zero();

        for (Material m : materiais) {
            double qtdDouble = m.getQuantidade() == null ? 0.0 : m.getQuantidade();
            BigDecimal quantidade = BigDecimal.valueOf(qtdDouble);

            // fatores ambientais
            residuo = residuo.add(quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.RESIDUO)));

            co2 = co2.add(quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.CO2)));

            agua = agua.add(quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.AGUA)));

            energia = energia.add(quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.ENERGIA)));

            // fatores financeiros
            var fatorFinOpt = fatorFinanceiroRepository.findByMaterialId(m.getId());
            if (fatorFinOpt.isPresent()) {
                var f = fatorFinOpt.get();
                if (f.getEconomiaDescarte() != null) {
                    economiaDescarte = economiaDescarte.add(
                            quantidade.multiply(f.getEconomiaDescarte())
                    );
                }
                if (f.getEconomiaSubstituicao() != null) {
                    economiaSubstituicao = economiaSubstituicao.add(
                            quantidade.multiply(f.getEconomiaSubstituicao())
                    );
                }
            }
        }

        BigDecimal economiaTotal = economiaDescarte.add(economiaSubstituicao);

        return new ResultadoMetricas(
                arred(residuo),
                arred(co2),
                arred(agua),
                arred(energia),
                arred(economiaDescarte),
                arred(economiaSubstituicao),
                arred(economiaTotal),
                calcularPorMaterial() // aqui você pode passar a lista tb se quiser
        );
    }

}