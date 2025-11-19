package com.pireco.sustentavel.metricas;

import java.math.BigDecimal;

public record ResultadoMetricas(
        BigDecimal residuoKg,
        BigDecimal co2T,
        BigDecimal aguaM3,
        BigDecimal energiaKwh,
        BigDecimal economiaDescarte,
        BigDecimal economiaSubstituicao,
        BigDecimal economiaTotal
) {}