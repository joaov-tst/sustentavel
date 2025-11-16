package com.pireco.sustentavel.config.auth;

import com.pireco.sustentavel.usuario.TipoUsuarioEnum;

public record UsuarioDTO(String email, String senha, String cpf, TipoUsuarioEnum tipo) {
}
