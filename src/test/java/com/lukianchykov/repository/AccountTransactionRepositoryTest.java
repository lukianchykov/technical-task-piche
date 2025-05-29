package com.lukianchykov.repository;

import com.lukianchykov.domain.Account;
import com.lukianchykov.domain.AccountTransaction;
import com.lukianchykov.domain.AccountTransaction.TransferType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountTransactionRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTransactionRepository transactionRepository;

    @Test
    @DisplayName("Should save account transaction")
    void testSaveTransaction() {
        Account account = Account.builder()
                .accountNumber("12345")
                .ownerName("Jane Smith")
                .balance(500.0)
                .build();
        accountRepository.save(account);

        AccountTransaction transaction = AccountTransaction.builder()
                .account(account)
                .type(TransferType.DEPOSIT)
                .amount(200.0)
                .description("Test deposit")
                .timestamp(LocalDateTime.now())
                .build();

        AccountTransaction saved = transactionRepository.save(transaction);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getType()).isEqualTo(TransferType.DEPOSIT);
        assertThat(saved.getAccount().getAccountNumber()).isEqualTo("12345");
    }
}
