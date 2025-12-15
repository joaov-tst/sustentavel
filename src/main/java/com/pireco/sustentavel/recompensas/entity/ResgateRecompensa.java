package com.pireco.sustentavel.recompensas.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "resgates_recompensa")
public class ResgateRecompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ID automático e aleatório
    private String id;

    private String recompensaId;
    private String clienteId;
    private int pontosGastos;
    private LocalDate dataResgate;
    private LocalDate validade;
    private String codigoVoucher;

    public ResgateRecompensa() {}

    public ResgateRecompensa(String recompensaId, String clienteId, int pontosGastos, LocalDate validade, String codigoVoucher) {
        this.recompensaId = recompensaId;
        this.clienteId = clienteId;
        this.pontosGastos = pontosGastos;
        this.dataResgate = LocalDate.now();
        this.validade = validade;
        this.codigoVoucher = codigoVoucher;
    }

    public String getId() {return id;}
    public String getRecompensaId() {return recompensaId;}
    public String getClienteId() {return clienteId;}
    public int getPontosGastos() {return pontosGastos;}
    public LocalDate getDataResgate() {return dataResgate;}
    public LocalDate getValidade() {return validade;}
    public String getCodigoVoucher() {return codigoVoucher;}

}
