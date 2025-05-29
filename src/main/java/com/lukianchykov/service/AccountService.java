package com.lukianchykov.service;

import java.util.List;
import java.util.UUID;

import com.lukianchykov.domain.Account;
import com.lukianchykov.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(String ownerName, double initialBalance) {
        Account account = Account.builder()
            .accountNumber(UUID.randomUUID().toString())
            .ownerName(ownerName)
            .balance(initialBalance)
            .transactions(List.of())
            .build();
        return accountRepository.save(account);
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

}

