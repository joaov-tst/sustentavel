package com.pireco.sustentavel.pontos;

import com.pireco.sustentavel.pontos.dto.SolicitacaoPontosRequest;
import com.pireco.sustentavel.pontos.dto.SolicitacaoPontosResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pontos/solicitacoes")
public class SolicitacaoPontosController {

    private final SolicitacaoPontosService service;

    public SolicitacaoPontosController(SolicitacaoPontosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SolicitacaoPontosResponse> criar(@RequestBody @Valid SolicitacaoPontosRequest request) {
        return ResponseEntity.ok(service.criar(request));
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<SolicitacaoPontosResponse>> listarMinhas() {
        return ResponseEntity.ok(service.listarMinhas());
    }
}
