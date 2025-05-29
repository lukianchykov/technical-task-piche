package com.lukianchykov.service;

import java.time.LocalDateTime;
import java.util.List;

import com.lukianchykov.domain.Account;
import com.lukianchykov.domain.AccountTransaction;
import com.lukianchykov.domain.AccountTransaction.TransferType;
import com.lukianchykov.repository.AccountRepository;
import com.lukianchykov.repository.AccountTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountTransactionService {

    private final AccountRepository accountRepository;

    private final AccountTransactionRepository transactionRepository;

    @Transactional
    public Account deposit(Account account, Double amount, String description) {
        account.setBalance(account.getBalance() + amount);

        AccountTransaction transaction = AccountTransaction.builder()
            .account(account)
            .type(TransferType.DEPOSIT)
            .amount(amount)
            .description(description)
            .timestamp(LocalDateTime.now())
            .build();

        transactionRepository.save(transaction);
        accountRepository.save(account);
        return account;
    }

    @Transactional
    public Account withdraw(Account account, Double amount, String description) {
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - amount);

        AccountTransaction transaction = AccountTransaction.builder()
            .account(account)
            .type(TransferType.WITHDRAW)
            .amount(amount)
            .description(description)
            .timestamp(LocalDateTime.now())
            .build();

        transactionRepository.save(transaction);
        accountRepository.save(account);
        return account;
    }

    @Transactional
    public List<Account> transfer(Account fromAccount, Account toAccount, Double amount, String description) {
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        AccountTransaction withdrawal = AccountTransaction.builder()
            .account(fromAccount)
            .type(TransferType.TRANSFER_OUT)
            .amount(amount)
            .description("To: " + toAccount.getAccountNumber() + " | " + description)
            .timestamp(LocalDateTime.now())
            .build();

        AccountTransaction deposit = AccountTransaction.builder()
            .account(toAccount)
            .type(TransferType.TRANSFER_IN)
            .amount(amount)
            .description("From: " + fromAccount.getAccountNumber() + " | " + description)
            .timestamp(LocalDateTime.now())
            .build();

        transactionRepository.saveAll(List.of(withdrawal, deposit));
        accountRepository.saveAll(List.of(fromAccount, toAccount));
        return List.of(fromAccount, toAccount);
    }
}
