package com.pireco.sustentavel.pontos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitacaoPontosRequest(
        @NotBlank String numeroComanda,
        @NotNull @Min(1) Integer pontosSolicitados,
        @NotBlank String descricao
) {}
