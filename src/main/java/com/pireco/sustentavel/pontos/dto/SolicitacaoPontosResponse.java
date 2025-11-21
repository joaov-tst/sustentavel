package com.pireco.sustentavel.pontos.dto;

import com.pireco.sustentavel.pontos.StatusSolicitacao;
import java.time.OffsetDateTime;

public record SolicitacaoPontosResponse(
        Long id,
        String numeroComanda,
        Integer pontosSolicitados,
        String descricao,
        OffsetDateTime dataSolicitacao,
        StatusSolicitacao status
) {}

