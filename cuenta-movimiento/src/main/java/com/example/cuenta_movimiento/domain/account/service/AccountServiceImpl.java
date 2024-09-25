package com.example.cuenta_movimiento.domain.account.service;

import com.example.cuenta_movimiento.domain.account.dto.AccountDTO;
import com.example.cuenta_movimiento.domain.account.entity.Account;
import com.example.cuenta_movimiento.domain.common.exception.AccountAlreadyExistsException;
import com.example.cuenta_movimiento.infraestructure.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        if (accountRepository.findByAccountNumber(accountDTO.getAccountNumber()) != null) {
            throw new AccountAlreadyExistsException("La cuenta con el número " + accountDTO.getAccountNumber() + " ya existe.");
        }
        Account account = modelMapper.map(accountDTO, Account.class);
        account = accountRepository.save(account);
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    @Transactional
    public AccountDTO updateAccount(Long accountId, AccountDTO accountDTO) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        modelMapper.map(accountDTO, account);
        accountRepository.save(account);
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public void deleteAccount(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
        } else {
            throw new ResourceNotFoundException("Account not found");
        }
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> modelMapper.map(account, AccountDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return modelMapper.map(account, AccountDTO.class);
    }
}