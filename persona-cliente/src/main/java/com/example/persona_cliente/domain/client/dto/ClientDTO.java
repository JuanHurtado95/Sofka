package com.example.persona_cliente.domain.client.dto;

import com.example.persona_cliente.domain.person.dto.PersonDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class ClientDTO extends PersonDTO {

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    private boolean status;
}