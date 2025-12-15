package com.pireco.sustentavel.recompensas.controller;

import com.pireco.sustentavel.recompensas.dto.ResgateRecompensaDTO;
import com.pireco.sustentavel.recompensas.entity.ResgateRecompensa;
import com.pireco.sustentavel.recompensas.service.ResgateService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recompensas")
public class ResgateController {

    private final ResgateService service;

    public ResgateController(ResgateService service) {
        this.service = service;
    }

    @PostMapping("/resgatar")
    public ResponseEntity<ResgateRecompensa> resgatar(@RequestBody ResgateRecompensaDTO dto) {
        return ResponseEntity.ok(service.realizarResgate(dto));
    }
}
