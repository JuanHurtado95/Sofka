package com.example.persona_cliente.domain.client.service;

import com.example.persona_cliente.domain.client.dto.ClientDTO;
import com.example.persona_cliente.domain.client.entity.Client;

import java.util.List;

public interface ClientService {
    ClientDTO createClient(ClientDTO clientDTO);
    ClientDTO updateClient(Long clientId, ClientDTO clientDTO);
    void deleteClient(Long clientId);
    ClientDTO getClientById(Long id);
    List<ClientDTO> getAllClients();
    ClientDTO getClientByIdentification(String identification);
}