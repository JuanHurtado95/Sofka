package com.example.persona_cliente.domain.client.entity;

import com.example.persona_cliente.domain.person.entity.Person;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class Client extends Person {


    @Column(name = "contrasena", nullable = false)
    private String password;

    @Column(name = "estado")
    private boolean status;
}
