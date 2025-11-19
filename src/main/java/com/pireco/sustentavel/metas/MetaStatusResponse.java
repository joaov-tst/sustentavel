package com.pireco.sustentavel.metas;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MetaStatusResponse(
        Long id,
        String nome,
        String indicador,
        String metrica,
        String periodicidade,
        LocalDate inicio,
        LocalDate fim,
        BigDecimal valorAlvo,
        BigDecimal valorAtual,
        BigDecimal progresso, // 0..1
        String status // ATINGIDA ou EM_ANDAMENTO
) {}
