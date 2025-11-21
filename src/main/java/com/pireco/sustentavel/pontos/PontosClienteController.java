package com.pireco.sustentavel.pontos;

import com.pireco.sustentavel.pontos.dto.ResumoPontosResponse;
import com.pireco.sustentavel.pontos.dto.VoucherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pontos")
public class PontosClienteController {

    private final SolicitacaoPontosService service;

    public PontosClienteController(SolicitacaoPontosService service) {
        this.service = service;
    }

    // Tela "Meus Pontos"
    @GetMapping("/resumo")
    public ResponseEntity<ResumoPontosResponse> resumo() {
        return ResponseEntity.ok(service.resumoPontosCliente());
    }

    // Botão "Resgatar recompensa" (gera o voucher)
    @PostMapping("/resgatar")
    public ResponseEntity<VoucherResponse> resgatar() {
        return ResponseEntity.ok(service.resgatarRecompensa());
    }
}
