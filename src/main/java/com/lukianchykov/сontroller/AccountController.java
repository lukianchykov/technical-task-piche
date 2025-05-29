package com.lukianchykov.сontroller;

import java.util.List;

import com.lukianchykov.dto.AccountDto;
import com.lukianchykov.dto.AccountCreateRequest;
import com.lukianchykov.facade.AccountFacade;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lukianchykov.utils.Constants.API_VERSION_PREFIX_V1;

@RestController
@RequestMapping(API_VERSION_PREFIX_V1 + "/accounts")
@ApiResponses(value = {
    @ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
    @ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
    @ApiResponse(code = 500, message = "The server is down. Please be with us."),
})
@RequiredArgsConstructor
public class AccountController {

    private final AccountFacade accountFacade;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public AccountDto createAccount(@RequestBody AccountCreateRequest request) {
        return accountFacade.createAccount(request);
    }

    @GetMapping("/{accountNumber}")
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public AccountDto getAccount(@PathVariable String accountNumber) {
        return accountFacade.getAccount(accountNumber);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public List<AccountDto> getAllAccounts() {
        return accountFacade.getAllAccounts();
    }

}
