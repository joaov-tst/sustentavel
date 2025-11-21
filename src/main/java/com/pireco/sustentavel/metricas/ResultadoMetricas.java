package com.pireco.sustentavel.metricas;

import java.math.BigDecimal;
import java.util.List;

public record ResultadoMetricas(
        BigDecimal residuo,
        BigDecimal co2,
        BigDecimal agua,
        BigDecimal energia,
        BigDecimal economiaDescarte,
        BigDecimal economiaSubstituicao,
        BigDecimal economiaTotal,
        List<ResumoMetricasMaterial> porMaterial)
{}