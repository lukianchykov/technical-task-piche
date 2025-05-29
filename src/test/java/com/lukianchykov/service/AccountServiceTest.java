package com.lukianchykov.service;

import com.lukianchykov.domain.Account;
import com.lukianchykov.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_shouldSaveAndReturnAccount() {
        String ownerName = "John Doe";
        double balance = 100.0;

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

        accountService.createAccount(ownerName, balance);

        verify(accountRepository).save(accountCaptor.capture());
        Account saved = accountCaptor.getValue();

        assertEquals(ownerName, saved.getOwnerName());
        assertEquals(balance, saved.getBalance());
        assertNotNull(saved.getAccountNumber());
        assertTrue(saved.getTransactions().isEmpty());
    }

    @Test
    void getAccount_shouldReturnAccountIfFound() {
        String accountNumber = UUID.randomUUID().toString();
        Account mockAccount = Account.builder().accountNumber(accountNumber).build();

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(mockAccount));

        Account result = accountService.getAccount(accountNumber);

        assertEquals(accountNumber, result.getAccountNumber());
    }

    @Test
    void getAccount_shouldThrowExceptionIfNotFound() {
        when(accountRepository.findByAccountNumber(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> accountService.getAccount("non-existing"));
    }

    @Test
    void getAllAccounts_shouldReturnAccountsList() {
        List<Account> accounts = List.of(new Account(), new Account());
        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAllAccounts();

        assertEquals(2, result.size());
    }
}
