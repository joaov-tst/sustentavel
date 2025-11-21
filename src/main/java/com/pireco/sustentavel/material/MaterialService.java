package com.pireco.sustentavel.material;

import com.pireco.sustentavel.material.dto.MaterialRequest;
import com.pireco.sustentavel.material.dto.MaterialResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository repository;

    // Criar material
    public MaterialResponse criar(MaterialRequest req) {
        Material m = new Material();
        m.setNome(req.nome());
        m.setQuantidade(req.quantidade());
        m.setUnidade(req.unidade());

        repository.save(m);

        return new MaterialResponse(
                m.getId(),
                m.getNome(),
                m.getQuantidade(),
                m.getUnidade(),
                m.getCriadoEm()
        );
    }

    // Listar todos
    public List<MaterialResponse> listarTodos() {
        return repository.findAll().stream()
                .map(m -> new MaterialResponse(
                        m.getId(),
                        m.getNome(),
                        m.getQuantidade(),
                        m.getUnidade(),
                        m.getCriadoEm()
                ))
                .toList();
    }

    // Buscar por ID
    public MaterialResponse buscarPorId(Long id) {
        Material m = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));

        return new MaterialResponse(
                m.getId(),
                m.getNome(),
                m.getQuantidade(),
                m.getUnidade(),
                m.getCriadoEm()
        );
    }

    // Atualizar
    public MaterialResponse atualizar(Long id, MaterialRequest req) {
        Material m = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));

        m.setNome(req.nome());
        m.setQuantidade(req.quantidade());
        m.setUnidade(req.unidade());

        repository.save(m);

        return new MaterialResponse(
                m.getId(),
                m.getNome(),
                m.getQuantidade(),
                m.getUnidade(),
                m.getCriadoEm()
        );
    }

    // Deletar
    public void deletar(Long id) {
        Material m = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));

        repository.delete(m);
    }
}

