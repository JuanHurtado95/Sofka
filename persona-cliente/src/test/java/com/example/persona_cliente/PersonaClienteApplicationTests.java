package com.example.persona_cliente;

import com.example.persona_cliente.domain.client.dto.ClientDTO;
import com.example.persona_cliente.domain.client.service.ClientService;
import com.example.persona_cliente.domain.common.exception.NoExistClientException;
import com.example.persona_cliente.infraestructure.client.controller.ClientController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ClientController.class)
class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientDTO clientDTO;

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
    }

    @Test
    void testCreateClient_Success() throws Exception {
        Mockito.when(clientService.createClient(any(ClientDTO.class))).thenReturn(clientDTO);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.identification").value("12345"))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void testCreateClient_InvalidInput() throws Exception {
        clientDTO.setName(""); // Invalid name

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllClients_Success() throws Exception {
        List<ClientDTO> clients = Collections.singletonList(clientDTO);
        Mockito.when(clientService.getAllClients()).thenReturn(clients);

        mockMvc.perform(get("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testGetClientById_Success() throws Exception {
        Mockito.when(clientService.getClientById(anyLong())).thenReturn(clientDTO);

        mockMvc.perform(get("/api/v1/clientes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.identification").value("12345"));
    }

    @Test
    void testGetClientById_NotFound() throws Exception {
        Mockito.when(clientService.getClientById(anyLong())).thenThrow(new NoExistClientException("Client not found"));

        mockMvc.perform(get("/api/v1/clientes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Client not found"));
    }

    @Test
    void testGetClientByIdentification_Success() throws Exception {
        Mockito.when(clientService.getClientByIdentification(any(String.class))).thenReturn(clientDTO);

        mockMvc.perform(get("/api/v1/clientes/identification/{identification}", "12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.identification").value("12345"));
    }

    @Test
    void testUpdateClient_Success() throws Exception {
        Mockito.when(clientService.updateClient(anyLong(), any(ClientDTO.class))).thenReturn(clientDTO);

        mockMvc.perform(put("/api/v1/clientes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.identification").value("12345"));
    }

    @Test
    void testDeleteClient_Success() throws Exception {
        mockMvc.perform(delete("/api/v1/clientes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteClient_NotFound() throws Exception {
        Mockito.doThrow(new NoExistClientException("Client not found")).when(clientService).deleteClient(anyLong());

        mockMvc.perform(delete("/api/v1/clientes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Client not found"));
    }
}