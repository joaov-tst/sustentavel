package com.pireco.sustentavel.metas;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome; // descrição da meta

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoIndicador indicador; // AMBIENTAL ou FINANCEIRO

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Metrica metrica; // RESIDUO, CO2, AGUA, ENERGIA, DESCARTE_RS, SUBSTITUICAO_RS, TOTAL_RS

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Periodicidade periodicidade; // MENSAL, TRIMESTRAL, ANUAL

    private LocalDate inicio; // só informativo por enquanto
    private LocalDate fim;

    @Column(nullable = false, precision = 18, scale = 4)
    private BigDecimal valorAlvo; // valor da meta (na unidade da métrica)

    @Column(length = 240)
    private String observacao;

    public enum TipoIndicador { AMBIENTAL, FINANCEIRO }

    public enum Metrica {
        RESIDUO, CO2, AGUA, ENERGIA,
        DESCARTE_RS, SUBSTITUICAO_RS, TOTAL_RS
    }

    public enum Periodicidade { MENSAL, TRIMESTRAL, ANUAL }

    // getters e setters
    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public TipoIndicador getIndicador() { return indicador; }
    public void setIndicador(TipoIndicador indicador) { this.indicador = indicador; }

    public Metrica getMetrica() { return metrica; }
    public void setMetrica(Metrica metrica) { this.metrica = metrica; }

    public Periodicidade getPeriodicidade() { return periodicidade; }
    public void setPeriodicidade(Periodicidade periodicidade) { this.periodicidade = periodicidade; }

    public LocalDate getInicio() { return inicio; }
    public void setInicio(LocalDate inicio) { this.inicio = inicio; }

    public LocalDate getFim() { return fim; }
    public void setFim(LocalDate fim) { this.fim = fim; }

    public BigDecimal getValorAlvo() { return valorAlvo; }
    public void setValorAlvo(BigDecimal valorAlvo) { this.valorAlvo = valorAlvo; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
