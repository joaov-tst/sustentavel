// PeriodoMetricasDTO.java
package com.pireco.sustentavel.metricas;

public record PeriodoMetricasDTO(
        String periodo,              // ex: "20/11", "11/2025", "2025"
        double residuoDesviado,      // kg ou unidade equivalente
        double co2Evitado,
        double aguaPreservada,
        double economiaDescarte,
        double substituicaoCompras,
        double totalReaproveitado,   // soma das quantidades
        double economiaTotal
) {}
