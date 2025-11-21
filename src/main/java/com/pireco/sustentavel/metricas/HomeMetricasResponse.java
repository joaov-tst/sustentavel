package com.pireco.sustentavel.metricas;

import java.util.List;

public record HomeMetricasResponse(
        Double totalReaproveitado,
        Double economiaGerada,
        Long clientesAtivos,
        Long pontosDistribuidos,
        List<DistribuicaoMaterialDTO> distribuicaoPorMaterial,
        ImpactoAmbientalDTO impactoAmbiental
) {}
