package com.lukianchykov.mapper;

import java.util.List;

import com.lukianchykov.domain.AccountTransaction;
import com.lukianchykov.dto.AccountTransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountTransactionMapper {

    @Mapping(target = "accountId", source = "account.id")
    AccountTransactionDto toDto(AccountTransaction transaction);

    List<AccountTransactionDto> toDtoList(List<AccountTransaction> transactions);
}
