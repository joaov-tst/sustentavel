package com.pireco.sustentavel.metas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metas")
@CrossOrigin(origins = "*") // se o front estiver em outro domínio/porta
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    // ===== criar meta (já existia) =====
    @PostMapping
    public ResponseEntity<Meta> criar(@RequestBody Meta meta) {
        Meta salva = metaService.salvar(meta);
        return ResponseEntity.ok(salva);
    }

    // ===== lista metas com status detalhado (já existia) =====
    @GetMapping("/status")
    public List<MetaStatusResponse> listarComStatus() {
        return metaService.listarComStatus();
    }

    // ===== NOVO: resumo para os cards da tela =====
    @GetMapping("/resumo-dashboard")
    public MetaResumoDashboardResponse resumoDashboard() {
        return metaService.resumoDashboard();
    }
}

