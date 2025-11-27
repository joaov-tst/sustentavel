package com.pireco.sustentavel.metas;

public record MetaResumoDashboardResponse(
        long metasAtivas,
        long metasConcluidas,
        long metasEmRisco
) {}