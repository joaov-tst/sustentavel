package com.pireco.sustentavel.pontos;

import com.pireco.sustentavel.usuario.UsuarioEntity;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "solicitacoes_pontos")
public class SolicitacaoPontos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroComanda;

    private Integer pontosSolicitados;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private OffsetDateTime dataSolicitacao;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    // ======= CONSTRUTORES =======

    public SolicitacaoPontos() {
    }

    public SolicitacaoPontos(String numeroComanda,
                             Integer pontosSolicitados,
                             String descricao,
                             UsuarioEntity usuario) {

        this.numeroComanda = numeroComanda;
        this.pontosSolicitados = pontosSolicitados;
        this.descricao = descricao;
        this.usuario = usuario;
        this.dataSolicitacao = OffsetDateTime.now();
        this.status = StatusSolicitacao.PENDENTE;
    }

    // ======= GETTERS =======

    public Long getId() {
        return id;
    }

    public String getNumeroComanda() {
        return numeroComanda;
    }

    public Integer getPontosSolicitados() {
        return pontosSolicitados;
    }

    public String getDescricao() {
        return descricao;
    }

    public OffsetDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    // ======= SETTERS NECESSÁRIOS =======

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
}

