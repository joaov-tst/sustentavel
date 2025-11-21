package com.pireco.sustentavel.usuario;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepositoryImplementation<UsuarioEntity, Long> {
    UsuarioEntity findByEmail(String username);

    // 👇 usa o NOME DO CAMPO da entidade: tipoUsuario
    long countByTipoUsuario(TipoUsuarioEnum tipoUsuario);
}


