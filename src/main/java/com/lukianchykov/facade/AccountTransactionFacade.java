package com.lukianchykov.facade;

import java.util.List;

import com.lukianchykov.domain.Account;
import com.lukianchykov.dto.TransactionRequest;
import com.lukianchykov.dto.TransactionResponse;
import com.lukianchykov.dto.TransferRequest;
import com.lukianchykov.dto.TransferResponse;
import com.lukianchykov.repository.AccountRepository;
import com.lukianchykov.service.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountTransactionFacade {

    private final AccountRepository accountRepository;

    private final AccountTransactionService accountTransactionService;

    public TransactionResponse deposit(TransactionRequest request) {
        var account = findAccount(request.getAccountNumber());
        Account depositedAccount = accountTransactionService.deposit(account, request.getAmount(), request.getDescription());
        return TransactionResponse.builder()
            .message("Deposit successful")
            .accountNumber(depositedAccount.getAccountNumber())
            .balance(depositedAccount.getBalance())
            .build();
    }

    public TransactionResponse withdraw(TransactionRequest request) {
        var account = findAccount(request.getAccountNumber());
        Account withdrawAccount = accountTransactionService.withdraw(account, request.getAmount(), request.getDescription());
        return TransactionResponse.builder()
            .message("Withdraw successful")
            .accountNumber(withdrawAccount.getAccountNumber())
            .balance(withdrawAccount.getBalance())
            .build();
    }

    public TransferResponse transfer(TransferRequest request) {
        var fromAccount = findAccount(request.getFromAccount());
        var toAccount = findAccount(request.getToAccount());
        List<Account> transferAccounts = accountTransactionService
            .transfer(fromAccount, toAccount, request.getAmount(), request.getDescription());
        return TransferResponse.builder()
            .message("Transfer successful")
            .fromAccount(transferAccounts.getFirst().getAccountNumber())
            .fromBalance(transferAccounts.getFirst().getBalance())
            .toAccount(transferAccounts.getLast().getAccountNumber())
            .toBalance(transferAccounts.getLast().getBalance())
            .build();
    }

    private Account findAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));
    }
}
