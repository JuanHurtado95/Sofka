package com.example.cuenta_movimiento.infraestructure.account.repository;

import com.example.cuenta_movimiento.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);
}
