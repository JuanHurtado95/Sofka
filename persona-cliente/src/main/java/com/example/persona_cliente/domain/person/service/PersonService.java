package com.example.persona_cliente.domain.person.service;

import com.example.persona_cliente.domain.person.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();
    Person findById(Long id);
    Person save(Person person);
    void delete(Long id);
}