package com.lukianchykov.facade;

import java.util.List;
import java.util.Optional;

import com.lukianchykov.domain.Account;
import com.lukianchykov.dto.TransactionRequest;
import com.lukianchykov.dto.TransactionResponse;
import com.lukianchykov.dto.TransferRequest;
import com.lukianchykov.dto.TransferResponse;
import com.lukianchykov.repository.AccountRepository;
import com.lukianchykov.service.AccountTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class AccountTransactionFacadeTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTransactionService accountTransactionService;

    @InjectMocks
    private AccountTransactionFacade facade;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deposit_shouldReturnTransactionResponse() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountNumber("ACC123");
        request.setAmount(100.0);
        request.setDescription("Deposit");

        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setBalance(1000.0);

        Account updatedAccount = new Account();
        updatedAccount.setAccountNumber("ACC123");
        updatedAccount.setBalance(1100.0);

        when(accountRepository.findByAccountNumber("ACC123")).thenReturn(Optional.of(account));
        when(accountTransactionService.deposit(account, 100.0, "Deposit")).thenReturn(updatedAccount);

        TransactionResponse response = facade.deposit(request);

        assertEquals("Deposit successful", response.getMessage());
        assertEquals("ACC123", response.getAccountNumber());
        assertEquals(1100.0, response.getBalance());
    }

    @Test
    void withdraw_shouldReturnTransactionResponse() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountNumber("ACC123");
        request.setAmount(50.0);
        request.setDescription("Withdraw");

        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setBalance(1000.0);

        Account updatedAccount = new Account();
        updatedAccount.setAccountNumber("ACC123");
        updatedAccount.setBalance(950.0);

        when(accountRepository.findByAccountNumber("ACC123")).thenReturn(Optional.of(account));
        when(accountTransactionService.withdraw(account, 50.0, "Withdraw")).thenReturn(updatedAccount);

        TransactionResponse response = facade.withdraw(request);

        assertEquals("Withdraw successful", response.getMessage());
        assertEquals("ACC123", response.getAccountNumber());
        assertEquals(950.0, response.getBalance());
    }

    @Test
    void transfer_shouldReturnTransferResponse() {
        TransferRequest request = new TransferRequest();
        request.setFromAccount("ACC1");
        request.setToAccount("ACC2");
        request.setAmount(200.0);
        request.setDescription("Transfer");

        Account fromAccount = new Account();
        fromAccount.setAccountNumber("ACC1");
        fromAccount.setBalance(1000.0);

        Account toAccount = new Account();
        toAccount.setAccountNumber("ACC2");
        toAccount.setBalance(500.0);

        Account fromUpdated = new Account();
        fromUpdated.setAccountNumber("ACC1");
        fromUpdated.setBalance(800.0);

        Account toUpdated = new Account();
        toUpdated.setAccountNumber("ACC2");
        toUpdated.setBalance(700.0);

        when(accountRepository.findByAccountNumber("ACC1")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNumber("ACC2")).thenReturn(Optional.of(toAccount));
        when(accountTransactionService.transfer(fromAccount, toAccount, 200.0, "Transfer"))
            .thenReturn(List.of(fromUpdated, toUpdated));

        TransferResponse response = facade.transfer(request);

        assertEquals("Transfer successful", response.getMessage());
        assertEquals("ACC1", response.getFromAccount());
        assertEquals(800.0, response.getFromBalance());
        assertEquals("ACC2", response.getToAccount());
        assertEquals(700.0, response.getToBalance());
    }

    @Test
    void findAccount_notFound_shouldThrow() {
        when(accountRepository.findByAccountNumber("UNKNOWN")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            facade.deposit(new TransactionRequest("UNKNOWN", 100.0, "desc"));
        });

        assertTrue(ex.getMessage().contains("Account not found"));
    }
}
