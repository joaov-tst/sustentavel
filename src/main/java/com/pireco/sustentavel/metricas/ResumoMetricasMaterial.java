package com.pireco.sustentavel.metricas;

import java.math.BigDecimal;

public record ResumoMetricasMaterial(
        String nomeMaterial,
        BigDecimal quantidadeTotal,
        String unidade,
        BigDecimal residuoKg,
        BigDecimal co2,
        BigDecimal aguaM3,
        BigDecimal energiaKwh,
        BigDecimal economiaDescarte,
        BigDecimal economiaSubstituicao,
        BigDecimal economiaTotal
) {}