package com.pireco.sustentavel.cliente.repositories;

import com.pireco.sustentavel.cliente.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface ClienteRepository  extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    Cliente findById(Integer id);
}
