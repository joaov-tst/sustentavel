package com.pireco.sustentavel.recompensas.service;
import com.pireco.sustentavel.cliente.repositories.ClienteRepository;
import com.pireco.sustentavel.recompensas.repository.ResgateRepository;
import com.pireco.sustentavel.recompensas.dto.ResgateRecompensaDTO;
import com.pireco.sustentavel.cliente.entities.Cliente;
import com.pireco.sustentavel.cliente.dto.ClienteDTO;

import com.pireco.sustentavel.recompensas.entity.ResgateRecompensa;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
public class ResgateService {

    private ResgateRepository repository;
    private ClienteRepository clienteRepository;

    public ResgateService(ResgateRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public ResgateRecompensa realizarResgate(ResgateRecompensaDTO dto) {

        Cliente cliente = clienteRepository.findById(Integer.parseInt(dto.getIdCliente()));

        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        if (cliente.getPontos() < dto.getPontosGastos()) {
            throw new RuntimeException("Saldo insuficiente.");
        }

        cliente.descontarPontos(dto.getPontosGastos());
        clienteRepository.save(cliente);

        LocalDate validade = LocalDate.now().plusDays(30);

        String codigo = gerarVoucher();

        ResgateRecompensa resgate = new ResgateRecompensa(
                dto.getIdRecompensa(),
                dto.getIdCliente(),
                dto.getPontosGastos(),
                validade,
                codigo
        );

        return repository.save(resgate);
    }

    private String gerarVoucher(){
        Random random = new Random();
        return "RESGATE-"+ random.nextInt(100, 999999) + "PIR";
    }
}
