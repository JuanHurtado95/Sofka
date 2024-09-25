package com.example.persona_cliente.domain.client.service;

import com.example.persona_cliente.domain.client.dto.ClientDTO;
import com.example.persona_cliente.domain.client.entity.Client;
import com.example.persona_cliente.domain.common.exception.CustomerIsAlreadyRegisterException;
import com.example.persona_cliente.domain.common.exception.NoExistClientException;
import com.example.persona_cliente.domain.notification.dto.NotificationMovementDTO;
import com.example.persona_cliente.infraestructure.client.repository.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private static final String CUSTOMER_IS_ALREADY_REGISTERED = "Cliente ya se encuentra registrado";
    private static final String CLIENT_NOT_FOUND = "Client not found";

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {

        String identification = clientDTO.getIdentification();
        if (clientRepository.findByIdentification(identification).isPresent()) {
            throw new CustomerIsAlreadyRegisterException(CUSTOMER_IS_ALREADY_REGISTERED);
        }

        Client client = modelMapper.map(clientDTO, Client.class);
        client.setStatus(true); // Estado por defecto
        Client savedClient = clientRepository.save(client);

        return modelMapper.map(savedClient, ClientDTO.class);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return clientRepository.findById(id)
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .orElseThrow(() -> new NoExistClientException(CLIENT_NOT_FOUND));
    }

    @Override
    public ClientDTO getClientByIdentification(String identification) {
        return clientRepository.findByIdentification(identification)
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .orElseThrow(() -> new NoExistClientException(CLIENT_NOT_FOUND));
    }

    @Override
    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NoExistClientException(CLIENT_NOT_FOUND));

        modelMapper.map(clientDTO, client);
        Client updatedClient = clientRepository.save(client);

        return modelMapper.map(updatedClient, ClientDTO.class);
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new NoExistClientException(CLIENT_NOT_FOUND);
        }
    }

    @KafkaListener(topics = "movements-topic", groupId = "group_id")
    public void listen(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            NotificationMovementDTO notificationMovementDTO = objectMapper.readValue(message, NotificationMovementDTO.class);
            ClientDTO clientDTO = getClientById(notificationMovementDTO.getClientId());

            System.out.println("Hola " + clientDTO.getName() + " , acabas de realizar un movimiento de " +
                    notificationMovementDTO.getMovementType() + " con valor de " +
                    notificationMovementDTO.getValue() + " el dia " + notificationMovementDTO.getDate());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}