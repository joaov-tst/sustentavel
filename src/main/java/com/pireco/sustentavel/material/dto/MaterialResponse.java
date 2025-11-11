package com.pireco.sustentavel.material.dto;

import com.pireco.sustentavel.material.Material;
import java.time.OffsetDateTime;

public record MaterialResponse(
        Long id,
        String nome,
        Double quantidade,
        String unidade,
        OffsetDateTime criadoEm
) {


}
