package com.pireco.sustentavel.pontos;

import com.pireco.sustentavel.pontos.dto.SolicitacaoPontosResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/pontos/solicitacoes")
public class SolicitacaoPontosAdminController {

    private final SolicitacaoPontosService service;

    public SolicitacaoPontosAdminController(SolicitacaoPontosService service) {
        this.service = service;
    }

    // 🔹 Lista todas as solicitações PENDENTES
    @GetMapping("/pendentes")
    public ResponseEntity<List<SolicitacaoPontosResponse>> listarPendentes() {
        return ResponseEntity.ok(service.listarPendentes());
    }

    // 🔹 Aprovar ou recusar uma solicitação
    // Exemplo: PATCH /admin/pontos/solicitacoes/5/status?status=APROVADA
    @PatchMapping("/{id}/status")
    public ResponseEntity<SolicitacaoPontosResponse> alterarStatus(
            @PathVariable Long id,
            @RequestParam StatusSolicitacao status
    ) {
        return ResponseEntity.ok(service.alterarStatus(id, status));
    }
}
