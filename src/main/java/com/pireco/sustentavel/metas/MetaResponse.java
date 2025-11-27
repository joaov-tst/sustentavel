package com.pireco.sustentavel.metas;

import com.pireco.sustentavel.metas.StatusMeta;
import com.pireco.sustentavel.metas.Meta.Periodicidade;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MetaResponse(
        Long id,
        String nome,
        String observacao,
        Periodicidade periodicidade,
        Long materialId,
        String nomeMaterial,
        BigDecimal valorAlvo,
        StatusMeta status,
        LocalDate inicio,
        LocalDate fim
) {}