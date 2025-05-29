package com.lukianchykov.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("owner_name")
    private String ownerName;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("transactions")
    private List<AccountTransactionDto> transactions;

}
