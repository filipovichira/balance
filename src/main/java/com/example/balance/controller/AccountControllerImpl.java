package com.example.balance.controller;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.balance.service.account.AccountService;

@RequestMapping("/account")
@RestController
public class AccountControllerImpl implements AccountController {

    private final String ACCOUNT_NUMBER_REGEXP = "^\\w{1,18}$";
    private final int SUM_FRACTION_PART = 2;

    private final AccountService accountService;

    @Autowired
    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    @Override
    public void create(@RequestParam("accountNumber") String accountNumber) {
        Assert.isTrue(Pattern.matches(ACCOUNT_NUMBER_REGEXP, accountNumber), "Invalid format for 'accountNumber'.");

        accountService.create(accountNumber);
    }

    @GetMapping("/balance")
    @Override
    public BigDecimal getBalance(@RequestParam("accountNumber") String accountNumber) {
        Assert.isTrue(Pattern.matches(ACCOUNT_NUMBER_REGEXP, accountNumber), "Invalid format for 'accountNumber'.");

        return accountService.get(accountNumber).getBalance();
    }

    @PutMapping("/replenish")
    @Override
    public void replenish(@RequestParam("accountNumber") String accountNumber, @RequestParam("sum") BigDecimal sum) {
        Assert.isTrue(Pattern.matches(ACCOUNT_NUMBER_REGEXP, accountNumber), "Invalid format for 'accountNumber'.");
        Assert.isTrue(sum.scale() <= SUM_FRACTION_PART, "Fractional part for 'sum' must not exceed 2 digits.");

        accountService.replenish(accountNumber, sum);
    }

    @PutMapping("/withdraw")
    @Override
    public void withdraw(@RequestParam("accountNumber") String accountNumber, @RequestParam("sum") BigDecimal sum) {
        Assert.isTrue(Pattern.matches(ACCOUNT_NUMBER_REGEXP, accountNumber), "Invalid format for 'accountNumber'.");
        Assert.isTrue(sum.scale() <= SUM_FRACTION_PART, "Fractional part for 'sum' must not exceed 2 digits.");

        accountService.withdraw(accountNumber, sum);
    }
}
