package com.pireco.sustentavel.pontos.dto;

import java.time.OffsetDateTime;

public record VoucherResponse(
        String codigo,
        String descricaoRecompensa,
        Integer pontosUtilizados,
        OffsetDateTime dataResgate,
        OffsetDateTime validade    //
) {}
