package com.lukianchykov.сontroller;

import com.lukianchykov.dto.TransactionRequest;
import com.lukianchykov.dto.TransactionResponse;
import com.lukianchykov.dto.TransferRequest;
import com.lukianchykov.dto.TransferResponse;
import com.lukianchykov.facade.AccountTransactionFacade;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionFacade accountTransactionFacade;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody TransactionRequest request) {
        TransactionResponse response = accountTransactionFacade.deposit(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody TransactionRequest request) {
        TransactionResponse response = accountTransactionFacade.withdraw(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        TransferResponse response = accountTransactionFacade.transfer(request);
        return ResponseEntity.ok(response);
    }

}
