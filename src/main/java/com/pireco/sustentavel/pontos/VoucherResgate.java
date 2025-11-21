package com.pireco.sustentavel.pontos;

import com.pireco.sustentavel.usuario.UsuarioEntity;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "vouchers_resgate")
public class VoucherResgate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;               // ex: GSR-ABCD1234
    private Integer pontosUtilizados;    // 100
    private String descricaoRecompensa;  // "Drink ou suco"
    private OffsetDateTime dataResgate;

    // 🔹 NOVO CAMPO
    private OffsetDateTime validade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    public VoucherResgate() {
    }

    public VoucherResgate(String codigo,
                          Integer pontosUtilizados,
                          String descricaoRecompensa,
                          UsuarioEntity usuario) {
        this.codigo = codigo;
        this.pontosUtilizados = pontosUtilizados;
        this.descricaoRecompensa = descricaoRecompensa;
        this.usuario = usuario;
        this.dataResgate = OffsetDateTime.now();

        // 🔹 define a validade: ex. 30 dias a partir do resgate
        this.validade = this.dataResgate.plusDays(30);
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public Integer getPontosUtilizados() { return pontosUtilizados; }
    public String getDescricaoRecompensa() { return descricaoRecompensa; }
    public OffsetDateTime getDataResgate() { return dataResgate; }

    // 🔹 GETTER DA VALIDADE
    public OffsetDateTime getValidade() { return validade; }
}
