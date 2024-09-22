package com.example.cuenta_movimiento.domain.movement.service;

import com.example.cuenta_movimiento.domain.account.entity.Account;
import com.example.cuenta_movimiento.domain.common.dto.NotificationMovementDTO;
import com.example.cuenta_movimiento.domain.common.exception.InsufficientBalanceException;
import com.example.cuenta_movimiento.domain.movement.dto.MovementDTO;
import com.example.cuenta_movimiento.domain.movement.entity.Movement;
import com.example.cuenta_movimiento.infraestructure.account.repository.AccountRepository;
import com.example.cuenta_movimiento.infraestructure.movement.repository.MovementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public MovementDTO createMovement(MovementDTO movementDTO) {
        Account account = accountRepository.findById(movementDTO.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // Verificar saldo disponible antes de crear el movimiento
        if (movementDTO.getMovementType().equals("Retiro") &&
                account.getAvailableBalance() < movementDTO.getValue()) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }

        Movement movement = new Movement();
        movement.setDate(movementDTO.getDate());
        movement.setMovementType(movementDTO.getMovementType());
        movement.setValue(movementDTO.getValue());
        movement.setAccount(account);

        // Actualizar el saldo disponible
        if (movementDTO.getMovementType().equals("Deposito")) {
            account.setAvailableBalance(account.getAvailableBalance() + movementDTO.getValue());
        } else {
            account.setAvailableBalance(account.getAvailableBalance() - movementDTO.getValue());
        }

        movementRepository.save(movement);
        accountRepository.save(account);

        NotificationMovementDTO notificationMovementDTO = new NotificationMovementDTO();
        notificationMovementDTO.setClientId(account.getClientId());
        notificationMovementDTO.setMovementType(movementDTO.getMovementType());
        notificationMovementDTO.setDate(movementDTO.getDate());
        notificationMovementDTO.setValue(movementDTO.getValue());
        notificationMovementDTO.setAccountId(movementDTO.getAccountId());

        try {
            String movementJson = objectMapper.writeValueAsString(notificationMovementDTO);
            kafkaTemplate.send("movements-topic", movementJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing MovementDTO to JSON", e);
        }

        return movementDTO;
    }

    @Override
    public MovementDTO updateMovement(Long movementId, MovementDTO movementDTO) {
        Movement movement = movementRepository.findById(movementId)
                .orElseThrow(() -> new ResourceNotFoundException("Movement not found"));
        movement.setDate(movementDTO.getDate());
        movement.setMovementType(movementDTO.getMovementType());
        movement.setValue(movementDTO.getValue());
        movementRepository.save(movement);
        return movementDTO;
    }

    @Override
    public void deleteMovement(Long movementId) {
        movementRepository.deleteById(movementId);
    }

    @Override
    public List<MovementDTO> getAllMovements() {
        return movementRepository.findAll()
                .stream()
                .map(movement -> {
                    MovementDTO dto = new MovementDTO();
                    dto.setDate(movement.getDate());
                    dto.setMovementType(movement.getMovementType());
                    dto.setValue(movement.getValue());
                    dto.setAccountId(movement.getAccount().getAccountId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public MovementDTO getMovementById(Long movementId) {
        Movement movement = movementRepository.findById(movementId)
                .orElseThrow(() -> new ResourceNotFoundException("Movement not found"));

        MovementDTO dto = new MovementDTO();
        dto.setDate(movement.getDate());
        dto.setMovementType(movement.getMovementType());
        dto.setValue(movement.getValue());

        return dto;
    }
}
