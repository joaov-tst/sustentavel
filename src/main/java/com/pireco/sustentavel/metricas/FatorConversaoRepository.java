package com.pireco.sustentavel.metricas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FatorConversaoRepository extends JpaRepository<FatorConversao, Long> {

    Optional<FatorConversao> findByMaterialIdAndTipo(Long materialId, FatorConversao.TipoMetrica tipo);
}

