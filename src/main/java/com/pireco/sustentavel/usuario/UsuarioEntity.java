package com.pireco.sustentavel.usuario;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "tb_usuario")
public class UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "cpf")
    private String cpf;

    @Getter
    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoUsuarioEnum tipoUsuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(tipoUsuario.equals(TipoUsuarioEnum.Admin)) return List.of(new SimpleGrantedAuthority("Admin"), new SimpleGrantedAuthority("Assistente"));
        if(tipoUsuario.equals(TipoUsuarioEnum.Assistente)) return List.of(new SimpleGrantedAuthority("Assistente"));
        return List.of(new SimpleGrantedAuthority("Cliente"));
    }

    public UsuarioEntity(){}

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public UsuarioEntity (String email, String senha, String cpf, String nome, TipoUsuarioEnum tipo){
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.nome = nome;
        this.tipoUsuario = tipo;
    }

    public String getNome() {
        return this.nome;
    }

    public TipoUsuarioEnum getTipo() {
        return tipoUsuario;
    }
}
