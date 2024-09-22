package com.example.persona_cliente.domain.person.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO {
    private String name;
    private String gender;
    private int age;
    private String identification;
    private String address;
    private String phone;

}
