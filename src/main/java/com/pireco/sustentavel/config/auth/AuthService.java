package com.pireco.sustentavel.config.auth;

import com.pireco.sustentavel.usuario.UsuarioEntity;
import com.pireco.sustentavel.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity user = repository.findByEmail(username);
        if(Objects.isNull(user)) throw new UsernameNotFoundException("Credenciais inválidas. Verifique email e senha e tente novamente.");
        return repository.findByEmail(username);
    }


}
