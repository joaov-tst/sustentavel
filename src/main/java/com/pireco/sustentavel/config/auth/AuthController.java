package com.pireco.sustentavel.config.auth;

import com.pireco.sustentavel.usuario.UsuarioEntity;
import com.pireco.sustentavel.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UsuarioDTO data){
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            Authentication auth = this.authenticationManager.authenticate(authenticationToken);
            UsuarioEntity user = (UsuarioEntity)auth.getPrincipal();
            return ResponseEntity.ok().body(LoginResponseDTO.converter(user, tokenService.genereteToken(user)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: " + e);
        }
    }

    @PostMapping(value = "/registrar")
    public ResponseEntity cadastrar(@RequestBody UsuarioDTO data){
        if(Objects.nonNull(usuarioRepository.findByEmail(data.email())))
            return ResponseEntity.badRequest().build();

        String senhaCripografada = passwordEncoder.encode(data.senha());
        UsuarioEntity novoUsuario = new UsuarioEntity(data.email(), senhaCripografada, data.cpf(), data.nome(), data.tipo());
        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
