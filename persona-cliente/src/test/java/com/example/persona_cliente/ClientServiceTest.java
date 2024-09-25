package com.example.persona_cliente;

import com.example.persona_cliente.domain.client.dto.ClientDTO;
import com.example.persona_cliente.domain.client.entity.Client;
import com.example.persona_cliente.domain.client.service.ClientServiceImpl;
import com.example.persona_cliente.domain.common.exception.CustomerIsAlreadyRegisterException;
import com.example.persona_cliente.domain.common.exception.NoExistClientException;
import com.example.persona_cliente.infraestructure.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientDTO clientDTO;
    private Client client;

    @BeforeEach
    void setUp() {
        clientDTO = new ClientDTO();
        clientDTO.setIdentification("12345");
        clientDTO.setName("John Doe");
        clientDTO.setGender("Masculino");
        clientDTO.setAge(30);
        clientDTO.setAddress("123 Main St");
        clientDTO.setPhone("1234567890");
        clientDTO.setPassword("securepassword");
        clientDTO.setStatus(true);

        client = new Client();
        client.setIdentification("12345");
        client.setName("John Doe");
        client.setGender("Masculino");
        client.setAge(30);
        client.setAddress("123 Main St");
        client.setPhone("1234567890");
        client.setStatus(true);
    }

    @Test
    void testCreateClient_Success() {
        when(clientRepository.findByIdentification(any(String.class))).thenReturn(Optional.empty());
        when(modelMapper.map(any(ClientDTO.class), any())).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(modelMapper.map(any(Client.class), any())).thenReturn(clientDTO);

        ClientDTO result = clientService.createClient(clientDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void testCreateClient_AlreadyRegistered() {
        when(clientRepository.findByIdentification(any(String.class))).thenReturn(Optional.of(client));

        assertThrows(CustomerIsAlreadyRegisterException.class, () -> clientService.createClient(clientDTO));

        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void testGetAllClients() {
        when(clientRepository.findAll()).thenReturn(Collections.singletonList(client));
        when(modelMapper.map(any(Client.class), any())).thenReturn(clientDTO);

        List<ClientDTO> clients = clientService.getAllClients();

        assertEquals(1, clients.size());
        assertEquals("John Doe", clients.get(0).getName());
    }

    @Test
    void testGetClientById_Success() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(modelMapper.map(any(Client.class), any())).thenReturn(clientDTO);

        ClientDTO result = clientService.getClientById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetClientById_NotFound() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoExistClientException.class, () -> clientService.getClientById(1L));
    }

    @Test
    void testUpdateClient_Success() {
        // Given
        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        lenient().when(modelMapper.map(any(ClientDTO.class), eq(Client.class))).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        lenient().when(modelMapper.map(any(Client.class), eq(ClientDTO.class))).thenReturn(clientDTO);

        ClientDTO result = clientService.updateClient(clientId, clientDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(clientRepository).findById(clientId);
        verify(clientRepository).save(client);
    }

    @Test
    void testUpdateClient_NotFound() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoExistClientException.class, () -> clientService.updateClient(1L, clientDTO));
    }

    @Test
    void testDeleteClient_Success() {
        when(clientRepository.existsById(anyLong())).thenReturn(true);

        clientService.deleteClient(1L);

        verify(clientRepository).deleteById(1L);
    }

    @Test
    void testDeleteClient_NotFound() {
        when(clientRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NoExistClientException.class, () -> clientService.deleteClient(1L));
    }

    // Test para el método listen
    @Test
    void testListen_ValidMessage() throws Exception {
        String message = "{\"clientId\":1,\"movementType\":\"Deposito\",\"value\":100.0,\"date\":\"2024-09-24\"}";
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(modelMapper.map(any(Client.class), any())).thenReturn(clientDTO);

        clientService.listen(message);

        assertEquals("John Doe", clientDTO.getName());
    }

    @Test
    void testListen_InvalidJson() {
        String invalidMessage = "Invalid JSON";

        assertThrows(RuntimeException.class, () -> clientService.listen(invalidMessage));
    }
}