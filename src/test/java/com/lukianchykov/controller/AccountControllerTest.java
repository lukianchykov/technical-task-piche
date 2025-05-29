package com.lukianchykov.controller;

import java.util.List;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukianchykov.dto.AccountDto;
import com.lukianchykov.dto.AccountCreateRequest;
import com.lukianchykov.facade.AccountFacade;
import com.lukianchykov.сontroller.AccountController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountFacade accountFacade;

    @MockBean
    private Tracer tracer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateAccount() throws Exception {
        AccountCreateRequest request = new AccountCreateRequest("John Doe", 1000.0);

        AccountDto response = AccountDto.builder()
            .id(1L)
            .accountNumber("ACC123")
            .ownerName("John Doe")
            .balance(1000.0)
            .transactions(List.of())
            .build();

        when(accountFacade.createAccount(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.owner_name").value(response.getOwnerName()))
            .andExpect(jsonPath("$.account_number").value(response.getAccountNumber()));
    }

    @Test
    void shouldGetAccountByNumber() throws Exception {
        String accountNumber = "ACC456";

        AccountDto account = AccountDto.builder()
            .id(2L)
            .accountNumber(accountNumber)
            .ownerName("Alice Smith")
            .balance(2500.0)
            .transactions(List.of())
            .build();

        when(accountFacade.getAccount(accountNumber)).thenReturn(account);

        mockMvc.perform(get("/api/v1/accounts/{accountNumber}", accountNumber))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.account_number").value(account.getAccountNumber()))
            .andExpect(jsonPath("$.owner_name").value(account.getOwnerName()));
    }

    @Test
    void shouldGetAllAccounts() throws Exception {
        List<AccountDto> accounts = List.of(
            AccountDto.builder()
                .id(1L)
                .accountNumber("ACC111")
                .ownerName("User One")
                .balance(100.0)
                .transactions(List.of())
                .build(),
            AccountDto.builder()
                .id(2L)
                .accountNumber("ACC222")
                .ownerName("User Two")
                .balance(200.0)
                .transactions(List.of())
                .build()
        );

        when(accountFacade.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/api/v1/accounts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(accounts.size()))
            .andExpect(jsonPath("$[0].account_number").value("ACC111"))
            .andExpect(jsonPath("$[1].account_number").value("ACC222"));
    }
}
