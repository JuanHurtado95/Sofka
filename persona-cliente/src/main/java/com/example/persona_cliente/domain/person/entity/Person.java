package com.example.persona_cliente.domain.person.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "genero", nullable = false)
    private String gender;

    @Column(name = "edad", nullable = false)
    private int age;

    @Column(name = "identificacion", nullable = false)
    private String identification;

    @Column(name = "direccion", nullable = false)
    private String address;

    @Column(name = "telefono", nullable = false)
    private String phone;
}
