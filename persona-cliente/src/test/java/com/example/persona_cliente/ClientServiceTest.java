package com.example.persona_cliente;

import com.example.persona_cliente.domain.client.dto.ClientDTO;
import com.example.persona_cliente.domain.client.entity.Client;
import com.example.persona_cliente.domain.client.service.ClientServiceImpl;
import com.example.persona_cliente.infraestructure.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1L);
        client.setName("Juan Pérez");
        client.setPassword("contrasena123");
        client.setStatus(true);
        client.setIdentification("1113965432");
    }

    @Test
    void testFindClientById() {

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ClientDTO foundClient = clientService.getClientById(1L);

        assertEquals("Juan Pérez", foundClient.getName());
        assertEquals("1113965432", foundClient.getIdentification());
        assertEquals(true, foundClient.isStatus());
    }
}
