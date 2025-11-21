package com.pireco.sustentavel.metricas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorios")
public class RelatoriosController {

    private final RelatoriosService relatoriosService;

    public RelatoriosController(RelatoriosService relatoriosService) {
        this.relatoriosService = relatoriosService;
    }

    @PostMapping("/desempenho")
    public ResponseEntity<RelatorioDesempenhoResponse> relatorioDesempenho(
            @RequestBody RelatorioFiltroRequest filtro
    ) {
        var resp = relatoriosService.gerarRelatorio(filtro);
        return ResponseEntity.ok(resp);
    }
}

