package com.lukianchykov.service;

import com.lukianchykov.domain.Account;
import com.lukianchykov.domain.AccountTransaction;
import com.lukianchykov.repository.AccountRepository;
import com.lukianchykov.repository.AccountTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountTransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTransactionRepository transactionRepository;

    @InjectMocks
    private AccountTransactionService transactionService;

    private Account account1;
    private Account account2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account1 = Account.builder().balance(100.0).accountNumber("111").build();
        account2 = Account.builder().balance(50.0).accountNumber("222").build();
    }

    @Test
    void deposit_shouldIncreaseBalanceAndSaveTransaction() {
        double depositAmount = 50.0;

        transactionService.deposit(account1, depositAmount, "Top up");

        assertEquals(150.0, account1.getBalance());
        verify(transactionRepository).save(any(AccountTransaction.class));
        verify(accountRepository).save(account1);
    }

    @Test
    void withdraw_shouldDecreaseBalanceAndSaveTransaction() {
        double withdrawAmount = 30.0;

        transactionService.withdraw(account1, withdrawAmount, "ATM");

        assertEquals(70.0, account1.getBalance());
        verify(transactionRepository).save(any(AccountTransaction.class));
        verify(accountRepository).save(account1);
    }

    @Test
    void withdraw_shouldThrowExceptionWhenInsufficientFunds() {
        assertThrows(IllegalArgumentException.class, () ->
            transactionService.withdraw(account1, 200.0, "Too much")
        );
    }

    @Test
    void transfer_shouldMoveMoneyAndCreateTransactions() {
        double transferAmount = 20.0;

        List<Account> result = transactionService.transfer(account1, account2, transferAmount, "Payment");

        assertEquals(80.0, result.get(0).getBalance());
        assertEquals(70.0, result.get(1).getBalance());

        verify(transactionRepository).saveAll(anyList());
        verify(accountRepository).saveAll(List.of(account1, account2));
    }

    @Test
    void transfer_shouldThrowExceptionWhenInsufficientFunds() {
        account1.setBalance(10.0);

        assertThrows(IllegalArgumentException.class, () ->
            transactionService.transfer(account1, account2, 20.0, "Invalid")
        );
    }
}
