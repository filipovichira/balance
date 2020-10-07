package com.example.balance.service.account;

import java.math.BigDecimal;

import com.example.balance.model.account.Account;

public interface AccountService {

    void create(String accountNumber);

    Account get(String accountNumber);

    void replenish(String accountNumber, BigDecimal sum);

    void withdraw(String accountNumber, BigDecimal sum);
}
