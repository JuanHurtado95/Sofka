package com.example.cuenta_movimiento.domain.account.service;

import com.example.cuenta_movimiento.domain.account.dto.AccountDTO;
import com.example.cuenta_movimiento.domain.account.entity.Account;
import com.example.cuenta_movimiento.infraestructure.account.repository.AccountRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setInitialBalance(accountDTO.getInitialBalance());
        account.setAvailableBalance(accountDTO.getInitialBalance());
        account.setStatus(accountDTO.isStatus());
        account.setClientId(accountDTO.getClientId()); // Asegúrate de establecer el cliente

        account = accountRepository.save(account);
        return accountDTO; // Convertir de vuelta a DTO si es necesario
    }

    @Override
    public AccountDTO updateAccount(Long accountId, AccountDTO accountDTO) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setInitialBalance(accountDTO.getInitialBalance());
        // No se debe cambiar el saldo disponible al actualizar
        account.setStatus(accountDTO.isStatus());
        accountRepository.save(account);
        return accountDTO; // Retornar el DTO actualizado
    }

    @Override
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> {
                    AccountDTO dto = new AccountDTO();
                    dto.setAccountNumber(account.getAccountNumber());
                    dto.setAccountType(account.getAccountType());
                    dto.setInitialBalance(account.getInitialBalance());
                    dto.setAvailableBalance(account.getAvailableBalance());
                    dto.setClientId(account.getClientId());
                    // Se puede agregar la lógica para incluir el cliente
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        AccountDTO dto = new AccountDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getAccountType());
        dto.setInitialBalance(account.getInitialBalance());
        dto.setAvailableBalance(account.getAvailableBalance());
        dto.setStatus(account.getStatus()); // Asignar el estado

        return dto; // Retornar el DTO
    }
}