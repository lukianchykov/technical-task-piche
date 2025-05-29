package com.lukianchykov.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTransactionDto {

    private Long id;

    @JsonProperty("account_id")
    private Long accountId;

    private TransferType type;

    private Double amount;

    private String description;

    private LocalDateTime timestamp;

    public enum TransferType {
        DEPOSIT, WITHDRAW, TRANSFER_IN, TRANSFER_OUT
    }
}
