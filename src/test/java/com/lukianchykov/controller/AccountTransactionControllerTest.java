package com.lukianchykov.controller;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukianchykov.dto.TransactionRequest;
import com.lukianchykov.dto.TransactionResponse;
import com.lukianchykov.dto.TransferRequest;
import com.lukianchykov.dto.TransferResponse;
import com.lukianchykov.facade.AccountTransactionFacade;
import com.lukianchykov.сontroller.AccountTransactionController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountTransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountTransactionFacade transactionFacade;

    @MockBean
    private Tracer tracer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldDeposit() throws Exception {
        TransactionRequest request = TransactionRequest.builder()
            .accountNumber("ACC123")
            .amount(500.0)
            .description("Deposit to savings")
            .build();

        TransactionResponse response = TransactionResponse.builder()
            .message("Deposit successful")
            .accountNumber("ACC123")
            .balance(1500.0)
            .build();

        when(transactionFacade.deposit(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/transactions/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(response.getMessage()))
            .andExpect(jsonPath("$.account_number").value(response.getAccountNumber()))
            .andExpect(jsonPath("$.balance").value(response.getBalance()));
    }

    @Test
    void shouldWithdraw() throws Exception {
        TransactionRequest request = TransactionRequest.builder()
            .accountNumber("ACC456")
            .amount(200.0)
            .description("ATM withdrawal")
            .build();

        TransactionResponse response = TransactionResponse.builder()
            .message("Withdrawal successful")
            .accountNumber("ACC456")
            .balance(800.0)
            .build();

        when(transactionFacade.withdraw(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/transactions/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(response.getMessage()))
            .andExpect(jsonPath("$.account_number").value(response.getAccountNumber()))
            .andExpect(jsonPath("$.balance").value(response.getBalance()));
    }

    @Test
    void shouldTransfer() throws Exception {
        TransferRequest request = TransferRequest.builder()
            .fromAccount("ACC001")
            .toAccount("ACC002")
            .amount(300.0)
            .description("Monthly transfer")
            .build();

        TransferResponse response = TransferResponse.builder()
            .message("Transfer successful")
            .fromAccount("ACC001")
            .toAccount("ACC002")
            .build();

        when(transactionFacade.transfer(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(response.getMessage()))
            .andExpect(jsonPath("$.from_account").value(response.getFromAccount()))
            .andExpect(jsonPath("$.to_account").value(response.getToAccount()));
    }

}
