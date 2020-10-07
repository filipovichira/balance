package com.example.balance.transaction;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.example.balance.TestConfig;
import com.example.balance.model.transaction.Transaction;
import com.example.balance.model.transaction.TransactionType;
import com.example.balance.repository.transaction.TransactionRepository;
import com.example.balance.service.transaction.TransactionService;

/**
 * Test for {@link TransactionService}
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
@Sql("/init.sql")
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void save() {
        Transaction transaction = createTransaction();
        transactionService.save(transaction);

        Transaction currentTransaction = transactionRepository.findById(transaction.getId()).orElse(null);

        assertThat(currentTransaction, notNullValue());

        assertThat(currentTransaction.getId(), notNullValue());
        assertThat(currentTransaction.getAccountNumber(), is(transaction.getAccountNumber()));
        assertThat(currentTransaction.getCreateDate().toEpochMilli(), is(transaction.getCreateDate().toEpochMilli()));
        assertThat(currentTransaction.getType(), is(transaction.getType()));
        assertThat(currentTransaction.getSum(), comparesEqualTo(transaction.getSum()));
    }

    @Test
    void saveWhenNotTransaction() {
        Transaction transaction = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> transactionService.save(transaction));
    }

    private Transaction createTransaction() {
        String accountNumber = "123456789123456789";

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setCreateDate(Instant.now());
        transaction.setType(TransactionType.REPLENISHMENT);
        transaction.setSum(BigDecimal.TEN);

        return transaction;
    }
}
