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
public class TransferResponse {

    private String message;

    @JsonProperty("from_account")
    private String fromAccount;

    @JsonProperty("from_balance")
    private Double fromBalance;

    @JsonProperty("to_account")
    private String toAccount;

    @JsonProperty("to_balance")
    private Double toBalance;
}
