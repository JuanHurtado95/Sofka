package com.example.persona_cliente.infraestructure.client.repository;

import com.example.persona_cliente.domain.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByIdentification(String identification);
}
