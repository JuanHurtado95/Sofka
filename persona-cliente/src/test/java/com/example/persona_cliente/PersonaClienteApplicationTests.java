package com.example.persona_cliente;

import com.example.persona_cliente.domain.client.entity.Client;
import com.example.persona_cliente.infraestructure.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PersonaClienteApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository; // Inyección del repositorio

    private Client client;

    @BeforeEach
    void setUp() {
        // Limpia la base de datos antes de cada prueba
        clientRepository.deleteAll();

        // Agrega un cliente para la prueba
        client = new Client();
        client.setName("Juan Pérez");
        client.setPassword("contrasena123");
        client.setStatus(true);
        client.setAddress("123 Calle Principal"); // Establecer la dirección
        client.setGender("Masculino"); // Establecer el género
        client.setAge(30); // Establecer la edad
        client.setIdentification("123456789"); // Establecer la identificación
        client.setPhone("1234567890"); // Establecer el teléfono
        clientRepository.save(client);
    }

    @Test
    void testGetClientById() throws Exception {
        mockMvc.perform(get("/api/v1/clientes/identification/{identification}", "123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.phone").value("1234567890"))
                .andExpect(jsonPath("$.status").value(true));
    }
}
