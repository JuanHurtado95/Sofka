package com.example.persona_cliente.domain.person.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "El género no puede estar vacío")
    @Pattern(regexp = "Masculino|Femenino", message = "El género debe ser 'Masculino' o 'Femenino'")
    private String gender;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 150, message = "La edad no puede ser mayor a 150 años")
    private int age;

    @NotBlank(message = "La identificación no puede estar vacía")
    @Size(min = 5, max = 20, message = "La identificación debe tener entre 5 y 20 caracteres")
    private String identification;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String address;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "\\d{7,15}", message = "El número de teléfono debe contener entre 7 y 15 dígitos (Solo debe contener numeros)")
    private String phone;
}