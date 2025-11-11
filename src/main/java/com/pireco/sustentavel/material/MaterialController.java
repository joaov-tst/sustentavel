package com.pireco.sustentavel.material;

import com.pireco.sustentavel.material.dto.MaterialRequest;
import com.pireco.sustentavel.material.dto.MaterialResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/materiais")
public class MaterialController {
    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MaterialResponse> criar(
            @RequestBody @Valid MaterialRequest body,
            UriComponentsBuilder uri
    ) {
        MaterialResponse resp = service.criar(body);
        return ResponseEntity
                .created(uri.path("/api/materiais/{id}").buildAndExpand(resp.id()).toUri())
                .body(resp);
    }







}
