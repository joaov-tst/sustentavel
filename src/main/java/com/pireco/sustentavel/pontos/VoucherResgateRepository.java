package com.pireco.sustentavel.pontos;

import com.pireco.sustentavel.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherResgateRepository extends JpaRepository<VoucherResgate, Long> {

    List<VoucherResgate> findByUsuario(UsuarioEntity usuario);
}

