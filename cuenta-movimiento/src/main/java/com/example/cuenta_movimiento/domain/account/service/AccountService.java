package com.example.cuenta_movimiento.domain.account.service;

import com.example.cuenta_movimiento.domain.account.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO updateAccount(Long accountId, AccountDTO accountDTO);
    void deleteAccount(Long accountId);
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long accountId);
}
