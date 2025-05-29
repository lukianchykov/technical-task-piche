package com.lukianchykov.сontroller;

import com.lukianchykov.dto.TransactionRequest;
import com.lukianchykov.dto.TransactionResponse;
import com.lukianchykov.dto.TransferRequest;
import com.lukianchykov.dto.TransferResponse;
import com.lukianchykov.facade.AccountTransactionFacade;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lukianchykov.utils.Constants.API_VERSION_PREFIX_V1;

@RestController
@RequestMapping(API_VERSION_PREFIX_V1 + "/transactions")
@ApiResponses(value = {
    @ApiResponse(code = 400, message = "This is a bad request, please follow the API documentation for the proper request format"),
    @ApiResponse(code = 401, message = "Due to security constraints, your access request cannot be authorized"),
    @ApiResponse(code = 500, message = "The server is down. Please be with us."),
})
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionFacade accountTransactionFacade;

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody TransactionRequest request) {
        TransactionResponse response = accountTransactionFacade.deposit(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody TransactionRequest request) {
        TransactionResponse response = accountTransactionFacade.withdraw(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request) {
        TransferResponse response = accountTransactionFacade.transfer(request);
        return ResponseEntity.ok(response);
    }

}
