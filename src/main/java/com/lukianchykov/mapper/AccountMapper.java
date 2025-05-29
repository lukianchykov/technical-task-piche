package com.lukianchykov.mapper;

import java.util.List;

import com.lukianchykov.domain.Account;
import com.lukianchykov.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AccountTransactionMapper.class)
public interface AccountMapper {

    @Mapping(target = "transactions", source = "transactions")
    AccountDto toDto(Account account);

    List<AccountDto> toListDto(List<Account> accounts);
}