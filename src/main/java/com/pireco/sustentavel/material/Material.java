package com.pireco.sustentavel.material;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "materiais")
public class Material {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    private Double quantidade;

    @Column(length = 10)
    private String unidade;

    private OffsetDateTime criadoEm;

    @PrePersist
    public void antesDeSalvar() {
        this.criadoEm = OffsetDateTime.now();
    }

    // getters e setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    public OffsetDateTime getCriadoEm() { return criadoEm; }
}
