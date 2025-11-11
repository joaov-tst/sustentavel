package com.pireco.sustentavel.material;

import com.pireco.sustentavel.material.dto.MaterialRequest;
import com.pireco.sustentavel.material.dto.MaterialResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialService {
    private final MaterialRepository repo;

    public MaterialService(MaterialRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public MaterialResponse criar(MaterialRequest req) {
        Material m = new Material();
        m.setNome(req.nome());
        m.setQuantidade(req.quantidade());
        m.setUnidade(req.unidade());
        m = repo.save(m);

        return new MaterialResponse(
                m.getId(), m.getNome(), m.getQuantidade(), m.getUnidade(), m.getCriadoEm()
        );
    }



}
