package com.example.balance.controller;

import java.math.BigDecimal;

public interface AccountController {

    void create(String accountNumber);

    BigDecimal getBalance(String accountNumber);

    void replenish(String accountNumber, BigDecimal sum);

    void withdraw(String accountNumber, BigDecimal sum);
}
