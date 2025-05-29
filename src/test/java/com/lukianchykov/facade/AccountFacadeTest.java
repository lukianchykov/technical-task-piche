package com.lukianchykov.facade;

import java.util.List;

import com.lukianchykov.domain.Account;
import com.lukianchykov.dto.AccountDto;
import com.lukianchykov.dto.AccountCreateRequest;
import com.lukianchykov.mapper.AccountMapper;
import com.lukianchykov.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountFacadeTest {

    @Mock
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountFacade accountFacade;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_shouldReturnDto() {
        AccountCreateRequest request = new AccountCreateRequest("John Doe", 1000.0);
        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setOwnerName("John Doe");
        account.setBalance(1000.0);
        AccountDto dto = AccountDto.builder()
            .accountNumber("ACC123")
            .ownerName("John Doe")
            .balance(1000.0)
            .build();

        when(accountService.createAccount("John Doe", 1000.0)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(dto);

        AccountDto result = accountFacade.createAccount(request);

        assertEquals(dto, result);
        verify(accountService).createAccount("John Doe", 1000.0);
        verify(accountMapper).toDto(account);
    }

    @Test
    void getAccount_shouldReturnDto() {
        String accNum = "ACC123";
        Account account = new Account();
        account.setAccountNumber(accNum);
        AccountDto dto = AccountDto.builder().accountNumber(accNum).build();

        when(accountService.getAccount(accNum)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(dto);

        AccountDto result = accountFacade.getAccount(accNum);

        assertEquals(dto, result);
        verify(accountService).getAccount(accNum);
        verify(accountMapper).toDto(account);
    }

    @Test
    void getAllAccounts_shouldReturnDtoList() {
        Account account1 = new Account();
        Account account2 = new Account();
        List<Account> accounts = List.of(account1, account2);
        List<AccountDto> dtos = List.of(
            AccountDto.builder().accountNumber("ACC1").build(),
            AccountDto.builder().accountNumber("ACC2").build()
        );

        when(accountService.getAllAccounts()).thenReturn(accounts);
        when(accountMapper.toListDto(accounts)).thenReturn(dtos);

        List<AccountDto> result = accountFacade.getAllAccounts();

        assertEquals(dtos, result);
        verify(accountService).getAllAccounts();
        verify(accountMapper).toListDto(accounts);
    }
}
