package com.example.persona_cliente.infraestructure.person.repository;

import com.example.persona_cliente.domain.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
