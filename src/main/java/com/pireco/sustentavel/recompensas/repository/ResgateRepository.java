package com.pireco.sustentavel.recompensas.repository;

import com.pireco.sustentavel.cliente.entities.Cliente;
import com.pireco.sustentavel.pontos.VoucherResgate;
import com.pireco.sustentavel.usuario.UsuarioEntity;
import com.pireco.sustentavel.recompensas.entity.ResgateRecompensa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResgateRepository extends JpaRepository<ResgateRecompensa, String> {
    ResgateRecompensa findByCodigoVoucher(String codigoVoucher);
}
