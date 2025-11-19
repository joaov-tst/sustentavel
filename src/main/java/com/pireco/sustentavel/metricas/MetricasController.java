package com.pireco.sustentavel.metricas;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metricas")
public class MetricasController {

    private final CalculoMetricasService calculoMetricasService;

    public MetricasController(CalculoMetricasService calculoMetricasService) {
        this.calculoMetricasService = calculoMetricasService;
    }

    @GetMapping
    public ResultadoMetricas obterMetricas() {
        return calculoMetricasService.calcular();
    }
}

