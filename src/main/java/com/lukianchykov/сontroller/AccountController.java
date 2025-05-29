package com.lukianchykov.сontroller;

import java.util.List;

import com.lukianchykov.dto.AccountDto;
import com.lukianchykov.dto.CreateAccountRequest;
import com.lukianchykov.facade.AccountFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountFacade accountFacade;

    @PostMapping
    public AccountDto createAccount(@RequestBody CreateAccountRequest request) {
        return accountFacade.createAccount(request);
    }

    @GetMapping("/{accountNumber}")
    public AccountDto getAccount(@PathVariable String accountNumber) {
        return accountFacade.getAccount(accountNumber);
    }

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return accountFacade.getAllAccounts();
    }

}
