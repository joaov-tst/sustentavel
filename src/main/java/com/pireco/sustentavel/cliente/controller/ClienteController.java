package com.pireco.sustentavel.cliente.controller;

import com.pireco.sustentavel.cliente.dto.LoginDTO;
import com.pireco.sustentavel.cliente.dto.RegistroDTO;
import com.pireco.sustentavel.cliente.entities.Cliente;
import com.pireco.sustentavel.cliente.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClientService service;

    @PostMapping("/cadastrar")
    public Cliente cadastrar(@RequestBody RegistroDTO registroDTO) {
        return service.cadastrar(registroDTO);
    }

    @PostMapping("/login")
    public Cliente login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }

    @GetMapping("/{id}")
    public Cliente areaPessoal(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

}
