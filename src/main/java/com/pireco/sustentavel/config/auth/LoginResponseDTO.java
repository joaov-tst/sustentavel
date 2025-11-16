package com.pireco.sustentavel.config.auth;

import com.pireco.sustentavel.usuario.UsuarioEntity;

public record LoginResponseDTO(UsuarioDTO.UserResponseDTO usuario, String token) {

    public static LoginResponseDTO converter(UsuarioEntity usuario, String token){
        return new LoginResponseDTO(new UsuarioDTO.UserResponseDTO(usuario), token);
    }
}
