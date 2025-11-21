package com.pireco.sustentavel.metricas;

import java.time.LocalDate;

// corpo que o FRONT vai mandar no POST
public record RelatorioFiltroRequest(
        LocalDate inicio,          // obrigatório
        LocalDate fim,             // obrigatório
        String categoria,          // opcional: "Papel", "Óleo", "Todos os Materiais"
        String tipoPeriodo         // "DIA", "MES", "ANO"  (default: DIA)
) {}
