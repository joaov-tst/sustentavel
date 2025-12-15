package com.pireco.sustentavel.recompensas.dto;

public class ResgateRecompensaDTO {
    private String idRecompensa;
    private String idCliente;
    private Integer pontosGastos;
    private String dataResgate;
    private String validade;

    public String getIdRecompensa() {
        return idRecompensa;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public Integer getPontosGastos() {
        return pontosGastos;
    }
}
