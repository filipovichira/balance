package com.example.balance.exception;

import java.math.BigDecimal;

public class BalanceExceededException extends RuntimeException {

    public BalanceExceededException() {
    }

    public BalanceExceededException(String accountNumber, BigDecimal sum) {
        super(String.format("Withdrawal sum '%s' exceeded balance for '%s' account.", sum, accountNumber));
    }
}
