package com.example.cuenta_movimiento.domain.common.configure;

import com.example.cuenta_movimiento.domain.account.dto.AccountDTO;
import com.example.cuenta_movimiento.domain.account.entity.Account;
import com.example.cuenta_movimiento.domain.movement.dto.MovementDTO;
import com.example.cuenta_movimiento.domain.movement.entity.Movement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<Movement, MovementDTO> movementMap = new PropertyMap<Movement, MovementDTO>() {
            @Override
            protected void configure() {
                map().setAccountId(source.getAccount().getAccountId());
            }
        };

        modelMapper.addMappings(movementMap);

        return modelMapper;
    }
}
