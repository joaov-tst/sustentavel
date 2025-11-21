package com.pireco.sustentavel.metricas;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class HomePublicController {

    private final CalculoMetricasService calculoMetricasService;

    public HomePublicController(CalculoMetricasService calculoMetricasService) {
        this.calculoMetricasService = calculoMetricasService;
    }

    @GetMapping("/home-metricas")
    public HomeMetricasResponse obterHomeMetricas() {
        return calculoMetricasService.calcularHomeMetricas();
    }
}
