package com.pireco.sustentavel.metas;

import com.pireco.sustentavel.metas.StatusMeta;
import com.pireco.sustentavel.metas.Meta.Periodicidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MetaRequest(
        @NotBlank String nome,               // Título da meta
        String observacao,                  // Descrição
        @NotNull Periodicidade periodicidade, // MENSAL, TRIMESTRAL, ANUAL
        Long materialId,                    // null = Todos
        @NotNull @Positive BigDecimal valorAlvo, // Valor da meta (kg/L)
        @NotNull StatusMeta status,         // ATIVA, CONCLUIDA, EM_RISCO
        @NotNull LocalDate inicio,
        @NotNull LocalDate fim
) {}
