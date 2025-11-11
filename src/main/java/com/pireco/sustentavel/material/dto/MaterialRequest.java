package com.pireco.sustentavel.material.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record MaterialRequest(
        @NotBlank String nome,
        @PositiveOrZero Double quantidade,
        @NotBlank String unidade
) {}

