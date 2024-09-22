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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private static final String CUSTOMER_IS_ALREADY_REGISTRED = "Cliente ya se encuentra registrado";

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {

        String identification = clientDTO.getIdentification();
        if (clientRepository.findByIdentification(identification).isPresent()) {
            throw new CustomerIsAlreadyRegisterException(CUSTOMER_IS_ALREADY_REGISTRED);
        }
        // Mapeo de ClientDTO a Client
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setGender(clientDTO.getGender());
        client.setAge(clientDTO.getAge());
        client.setIdentification(clientDTO.getIdentification());
        client.setAddress(clientDTO.getAddress());
        client.setPhone(clientDTO.getPhone());
        client.setPassword(clientDTO.getPassword());
        client.setStatus(true); // Por defecto, el estado puede ser activo

        Client savedClient = clientRepository.save(client);

        return mapToDTO(savedClient);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            return mapToDTO(clientOptional.get());
        } else {
            throw new NoExistClientException("Client not found");
        }
    }

    @Override
    public ClientDTO getClientByIdentification(String identification) {
        Optional<Client> clientOptional = clientRepository.findByIdentification(identification);
        if (clientOptional.isPresent()) {
            return mapToDTO(clientOptional.get());
        } else {
            throw new NoExistClientException("Client not found");
        }
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setName(clientDTO.getName());
            client.setGender(clientDTO.getGender());
            client.setAge(clientDTO.getAge());
            client.setIdentification(clientDTO.getIdentification());
            client.setAddress(clientDTO.getAddress());
            client.setPhone(clientDTO.getPhone());
            client.setPassword(clientDTO.getPassword());
            // No se actualiza el estado por motivos de seguridad

            Client updatedClient = clientRepository.save(client);
            return mapToDTO(updatedClient);
        } else {
            throw new NoExistClientException("Client not found");
        }
    }

    @Override
    public void deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new NoExistClientException("Client not found");
        }
    }

    @KafkaListener(topics = "movements-topic", groupId = "group_id")
    private void listen(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            NotificationMovementDTO notificationMovementDTO = objectMapper.readValue(message, NotificationMovementDTO.class);
            ClientDTO clientDTO = getClientById(notificationMovementDTO.getClientId());

            System.out.println("Señor " + clientDTO.getName() + " acaba de realizar un movimiento de " +
                    notificationMovementDTO.getMovementType() + " con valor de " +
                    notificationMovementDTO.getValue() + " el dia " + notificationMovementDTO.getDate());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ClientDTO mapToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setGender(client.getGender());
        clientDTO.setAge(client.getAge());
        clientDTO.setIdentification(client.getIdentification());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setPhone(client.getPhone());
        clientDTO.setStatus(client.isStatus());
        return clientDTO;
    }
}