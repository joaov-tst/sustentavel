package com.pireco.sustentavel.config.auth;

import com.pireco.sustentavel.usuario.TipoUsuarioEnum;
import com.pireco.sustentavel.usuario.UsuarioEntity;

public record UsuarioDTO(String email, String senha, String cpf, String nome, TipoUsuarioEnum tipo) {

    public record UserResponseDTO(String nome, TipoUsuarioEnum tipo){
        public UserResponseDTO(UsuarioEntity usuario){
            this(usuario.getNome(), usuario.getTipo());
        }
    }
}
