package com.pireco.sustentavel.metricas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FatorFinanceiroRepository extends JpaRepository<FatorFinanceiro, Long> {

    Optional<FatorFinanceiro> findByMaterialId(Long materialId);
}
