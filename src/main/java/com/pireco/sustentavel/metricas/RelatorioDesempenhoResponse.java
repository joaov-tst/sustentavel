// RelatorioDesempenhoResponse.java
package com.pireco.sustentavel.metricas;

import java.util.List;

public record RelatorioDesempenhoResponse(
        List<PeriodoMetricasDTO> periodos,
        List<DistribuicaoMaterialDTO> distribuicaoPorCategoria,
        long registrosNoPeriodo
) {}
