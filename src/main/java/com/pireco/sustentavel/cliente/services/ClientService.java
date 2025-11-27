package com.pireco.sustentavel.cliente.services;

import com.pireco.sustentavel.cliente.dto.LoginDTO;
import com.pireco.sustentavel.cliente.entities.Cliente;
import com.pireco.sustentavel.cliente.dto.RegistroDTO;
import com.pireco.sustentavel.cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientService {

    @Autowired
    private ClienteRepository repository;


    public Cliente cadastrar(RegistroDTO registroDTO){
        Cliente cliente = new Cliente();
        cliente.setNome(registroDTO.nome());
        cliente.setEmail(registroDTO.email());
        cliente.setSenha(registroDTO.senha());

        return repository.save(cliente);
    }

    public Cliente login(LoginDTO loginDTO){
        Cliente cliente = repository.findByEmail(loginDTO.email())
                .orElseThrow(() -> new RuntimeException("Email não encontrado"));

        if(!cliente.getSenha().equals(loginDTO.senha())) throw new RuntimeException("Senha inválida");

        return cliente;
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

}
