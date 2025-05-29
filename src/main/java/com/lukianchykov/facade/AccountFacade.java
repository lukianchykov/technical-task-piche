package com.lukianchykov.facade;

import java.util.List;

import com.lukianchykov.domain.Account;
import com.lukianchykov.dto.AccountDto;
import com.lukianchykov.dto.AccountCreateRequest;
import com.lukianchykov.mapper.AccountMapper;
import com.lukianchykov.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    public AccountDto createAccount(AccountCreateRequest request) {
        var account = accountService.createAccount(request.getOwnerName(), request.getInitialBalance());
        return accountMapper.toDto(account);
    }

    public AccountDto getAccount(String accountNumber) {
        var account = accountService.getAccount(accountNumber);
        return accountMapper.toDto(account);
    }

    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return accountMapper.toListDto(accounts);
    }
}