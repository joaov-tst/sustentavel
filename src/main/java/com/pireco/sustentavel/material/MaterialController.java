package com.pireco.sustentavel.material;

import com.pireco.sustentavel.material.dto.MaterialRequest;
import com.pireco.sustentavel.material.dto.MaterialResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/materiais")
public class MaterialController {
    @Autowired
    private MaterialService service;

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

    // Listar todos os materiais
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // Buscar material por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        MaterialResponse resp = service.buscarPorId(id);
        return ResponseEntity.ok(resp);
    }

    // Atualizar material
    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid MaterialRequest body
    ) {
        MaterialResponse resp = service.atualizar(id, body);
        return ResponseEntity.ok(resp);
    }

    // Deletar material (lógico, se quiser)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


}




