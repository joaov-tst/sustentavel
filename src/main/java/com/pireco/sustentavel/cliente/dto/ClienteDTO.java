package com.pireco.sustentavel.cliente.dto;

import com.pireco.sustentavel.cliente.entities.Cliente;
import jakarta.persistence.Column;
import org.springframework.beans.BeanUtils;

public class ClienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private int pontos;

    public ClienteDTO(){}

    public ClienteDTO(Cliente entity){
        BeanUtils.copyProperties(entity, this);
    }

}
