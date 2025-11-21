package com.pireco.sustentavel.pontos.dto;

import java.util.List;

public record ResumoPontosResponse(
        Integer saldoAtual,
        Integer totalAcumulado,
        Integer totalResgatado,
        Integer solicitacoesPendentes,
        List<TransacaoPontosResponse> historico // linha do “Histórico de Transações”
) {}
