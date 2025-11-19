package com.pireco.sustentavel.metricas;

import com.pireco.sustentavel.material.Material;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "fatores_financeiros",
        uniqueConstraints = @UniqueConstraint(columnNames = {"material_id"})
)
public class FatorFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Material material;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal economiaDescarte;

    @Column(precision = 10, scale = 2)
    private BigDecimal economiaSubstituicao;

    public Long getId() { return id; }

    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }

    public BigDecimal getEconomiaDescarte() { return economiaDescarte; }
    public void setEconomiaDescarte(BigDecimal economiaDescarte) { this.economiaDescarte = economiaDescarte; }

    public BigDecimal getEconomiaSubstituicao() { return economiaSubstituicao; }
    public void setEconomiaSubstituicao(BigDecimal economiaSubstituicao) { this.economiaSubstituicao = economiaSubstituicao; }
}
