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
public class TransferRequest {

    @JsonProperty("from_account")
    private String fromAccount;

    @JsonProperty("to_account")
    private String toAccount;

    private Double amount;

    private String description;
}
