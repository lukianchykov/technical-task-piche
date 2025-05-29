package com.lukianchykov.repository;

import com.lukianchykov.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testFindByAccountNumber() {
        String accountNumber = UUID.randomUUID().toString();
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .ownerName("John Doe")
                .balance(1000.0)
                .build();

        accountRepository.save(account);

        Optional<Account> result = accountRepository.findByAccountNumber(accountNumber);

        assertThat(result).isPresent();
        assertThat(result.get().getOwnerName()).isEqualTo("John Doe");
    }

    @Test
    void testFindByAccountNumber_NotFound() {
        Optional<Account> result = accountRepository.findByAccountNumber("non-existent");
        assertThat(result).isNotPresent();
    }
}
