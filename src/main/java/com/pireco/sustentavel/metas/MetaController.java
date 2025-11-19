package com.pireco.sustentavel.metas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metas")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @PostMapping
    public ResponseEntity<Meta> criar(@RequestBody Meta meta) {
        Meta salva = metaService.salvar(meta);
        return ResponseEntity.ok(salva);
    }

    @GetMapping("/status")
    public List<MetaStatusResponse> listarComStatus() {
        return metaService.listarComStatus();
    }
}
