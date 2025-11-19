package com.pireco.sustentavel.metricas;

import com.pireco.sustentavel.material.Material;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "fatores_conversao",
        uniqueConstraints = @UniqueConstraint(columnNames = {"material_id", "tipo"})
)
public class FatorConversao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Material material;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMetrica tipo;

    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal fator;

    private String fonte;

    public enum TipoMetrica {
        RESIDUO, CO2, AGUA, ENERGIA
    }

    public Long getId() { return id; }

    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }

    public TipoMetrica getTipo() { return tipo; }
    public void setTipo(TipoMetrica tipo) { this.tipo = tipo; }

    public BigDecimal getFator() { return fator; }
    public void setFator(BigDecimal fator) { this.fator = fator; }

    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }
}
