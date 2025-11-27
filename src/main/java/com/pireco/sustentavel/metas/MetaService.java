package com.pireco.sustentavel.metas;

import com.pireco.sustentavel.metricas.CalculoMetricasService;
import com.pireco.sustentavel.metricas.ResultadoMetricas;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final CalculoMetricasService calculoMetricasService;

    public MetaService(MetaRepository metaRepository,
                       CalculoMetricasService calculoMetricasService) {
        this.metaRepository = metaRepository;
        this.calculoMetricasService = calculoMetricasService;
    }

    // ===========================
    // SALVAR META
    // ===========================
    public Meta salvar(Meta meta) {
        return metaRepository.save(meta);
    }

    // ===========================
    // RESUMO PARA OS CARDS DA TELA
    // ===========================
    public MetaResumoDashboardResponse resumoDashboard() {
        long ativas = metaRepository.countByStatus(StatusMeta.ATIVA);
        long concluidas = metaRepository.countByStatus(StatusMeta.CONCLUIDA);
        long emRisco = metaRepository.countByStatus(StatusMeta.EM_RISCO);

        return new MetaResumoDashboardResponse(ativas, concluidas, emRisco);
    }

    // ===========================
    // LISTA METAS COM STATUS DETALHADO
    // ===========================
    public List<MetaStatusResponse> listarComStatus() {

        List<Meta> metas = metaRepository.findAll();

        ResultadoMetricas resultado = calculoMetricasService.calcular();

        List<MetaStatusResponse> resposta = new ArrayList<>();

        for (Meta meta : metas) {

            BigDecimal valorAtual = valorDaMetrica(meta, resultado);
            BigDecimal progresso = progresso(meta.getValorAlvo(), valorAtual);

            String status = progresso.compareTo(BigDecimal.ONE) >= 0
                    ? "ATINGIDA"
                    : "EM_ANDAMENTO";

            resposta.add(new MetaStatusResponse(
                    meta.getId(),
                    meta.getNome(),
                    meta.getIndicador().name(),
                    meta.getMetrica().name(),
                    meta.getPeriodicidade().name(),
                    meta.getInicio(),
                    meta.getFim(),
                    arred(meta.getValorAlvo()),
                    arred(valorAtual),
                    arred(progresso),
                    status
            ));
        }

        return resposta;
    }

    // ===========================
    // AUXILIARES
    // ===========================

    private BigDecimal valorDaMetrica(Meta meta, ResultadoMetricas r) {
        return switch (meta.getMetrica()) {
            case RESIDUO -> r.residuo();
            case CO2 -> r.co2();
            case AGUA -> r.agua();
            case ENERGIA -> r.energia();
            case DESCARTE_RS -> r.economiaDescarte();
            case SUBSTITUICAO_RS -> r.economiaSubstituicao();
            case TOTAL_RS -> r.economiaTotal();
        };
    }

    private BigDecimal progresso(BigDecimal alvo, BigDecimal atual) {
        if (alvo == null || alvo.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return atual.divide(alvo, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal arred(BigDecimal v) {
        return v.setScale(4, RoundingMode.HALF_UP);
    }
}
