package com.example.persona_cliente.domain.client.dto;

import com.example.persona_cliente.domain.person.dto.PersonDTO;
import lombok.*;

@Getter
@Setter
public class ClientDTO extends PersonDTO {
    private String password;
    private boolean status;
}