package com.pireco.sustentavel.pontos.dto;

import java.time.OffsetDateTime;

public record TransacaoPontosResponse(
        String descricao,
        OffsetDateTime data,
        Integer pontos // positivo = ganho, negativo = resgate
) {}
