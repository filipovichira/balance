package com.example.balance.account;

import java.math.BigDecimal;

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
import com.example.balance.exception.NotFoundException;
import com.example.balance.exception.BalanceExceededException;
import com.example.balance.model.account.Account;
import com.example.balance.service.account.AccountService;

/**
 * Test for {@link AccountService}
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
@Sql("/init.sql")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void create() {
        String accountNumber = "111111111111111111";
        BigDecimal sum = BigDecimal.ZERO;

        accountService.create(accountNumber);
        Account account = accountService.get(accountNumber);

        assertThat(account.getId(), notNullValue());
        assertThat(account.getNumber(), is(accountNumber));
        assertThat(account.getBalance(), comparesEqualTo(sum));
    }

    @Test
    public void createWhenNotAccountNumber() {
        String accountNumber = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.create(accountNumber));
    }

    @Test
    void get() {
        String accountNumber = "123456789123456789";

        Account account = accountService.get(accountNumber);

        assertThat(account.getNumber(), is(accountNumber));
    }

    @Test
    void getWhenNotAccountNumber() {
        String accountNumber = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.get(accountNumber));
    }

    @Test
    void getWhenAccountNotFound() {
        String accountNumber = "000000000000000000";

        Assertions.assertThrows(NotFoundException.class, () -> accountService.get(accountNumber));
    }

    @Test
    public void replenish() {
        String accountNumber = "123456789123456789";
        BigDecimal sum = BigDecimal.valueOf(100);
        BigDecimal afterReplenishmentBalance = BigDecimal.valueOf(1100);

        accountService.replenish(accountNumber, sum);
        Account account = accountService.get(accountNumber);

        assertThat(account.getNumber(), is(accountNumber));
        assertThat(account.getBalance(), comparesEqualTo(afterReplenishmentBalance));
    }

    @Test
    public void replenishWhenNotAccountNumber() {
        String accountNumber = null;
        BigDecimal sum = BigDecimal.valueOf(999999999.99);

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.replenish(accountNumber, sum));
    }

    @Test
    public void replenishWhenNotSum() {
        String accountNumber = "123456789123456789";
        BigDecimal sum = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.replenish(accountNumber, sum));
    }

    @Test
    public void replenishWhenNegativeSum() {
        String accountNumber = "123456789123456789";
        BigDecimal sum = BigDecimal.valueOf(-9999999999.99);

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.replenish(accountNumber, sum));
    }

    @Test
    public void replenishWhenAccountNotFound() {
        String accountNumber = "000000000000000000";
        BigDecimal sum = BigDecimal.valueOf(100);

        Assertions.assertThrows(NotFoundException.class, () -> accountService.replenish(accountNumber, sum));
    }

    @Test
    public void withdrawal() {
        String accountNumber = "123456789123456789";
        BigDecimal sum = BigDecimal.valueOf(100);
        BigDecimal afterWithdrawalBalance = BigDecimal.valueOf(900);

        accountService.withdraw(accountNumber, sum);
        Account account = accountService.get(accountNumber);

        assertThat(account.getNumber(), is(accountNumber));
        assertThat(account.getBalance(), comparesEqualTo(afterWithdrawalBalance));
    }

    @Test
    public void withdrawalWhenNotAccountNumber() {
        String accountNumber = null;
        BigDecimal sum = BigDecimal.valueOf(5000);

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(accountNumber, sum));
    }

    @Test
    public void withdrawalWhenWhenNotSum() {
        String accountNumber = "123456789123456789";
        BigDecimal sum = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(accountNumber, sum));
    }

    @Test
    public void withdrawalWhenNegativeSum() {
        String accountNumber = "123456789123456789";
        BigDecimal sum = BigDecimal.valueOf(-5);

        Assertions.assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(accountNumber, sum));
    }

    @Test
    public void withdrawalWhenBalanceExceeded() {
        String accountNumber = "123456789123456789";
        BigDecimal sum = BigDecimal.valueOf(5000);

        Assertions.assertThrows(BalanceExceededException.class, () -> accountService.withdraw(accountNumber, sum));
    }

    @Test
    public void withdrawalWhenAccountNotFound() {
        String accountNumber = "000000000000000000";
        BigDecimal sum = BigDecimal.valueOf(100);

        Assertions.assertThrows(NotFoundException.class, () -> accountService.withdraw(accountNumber, sum));
    }
}
