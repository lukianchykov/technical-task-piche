package com.lukianchykov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("description")
    private String description;
}