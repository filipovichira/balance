package com.example.balance.service.account;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.example.balance.exception.AlreadyExistsException;
import com.example.balance.exception.BalanceExceededException;
import com.example.balance.exception.NotFoundException;
import com.example.balance.model.account.Account;
import com.example.balance.model.transaction.Transaction;
import com.example.balance.model.transaction.TransactionType;
import com.example.balance.repository.account.AccountRepository;
import com.example.balance.service.transaction.TransactionService;

@Service
public class AccountServiceImpl implements AccountService {

    private final TransactionService transactionService;

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(TransactionService transactionService, AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public void create(String accountNumber) {
        Assert.notNull(accountNumber, "Argument 'accountNumber' must not be null.");

        if (accountRepository.findByNumber(accountNumber).isPresent()) {
            throw new AlreadyExistsException(String.format("Account '%s' already exists", accountNumber));
        }

        Account account = new Account(accountNumber);
        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Override
    public Account get(String accountNumber) {
        Assert.notNull(accountNumber, "Argument 'accountNumber' must not be null.");

        return accountRepository.findByNumber(accountNumber)
            .orElseThrow(() -> new NotFoundException(String.format("Account with number '%s' not found.", accountNumber)));
    }

    @Transactional
    @Override
    public void replenish(String accountNumber, BigDecimal sum) {
        Assert.notNull(accountNumber, "Argument 'accountNumber' must not be null.");
        Assert.notNull(sum, "Argument 'sum' must not be null.");
        Assert.isTrue(sum.compareTo(BigDecimal.ZERO) > 0, "Replenishment sum must be greater than zero.");

        Account account = accountRepository.findByNumberForWrite(accountNumber)
            .orElseThrow(() -> new NotFoundException(String.format("Account with number '%s' not found.", accountNumber)));

        account.setBalance(account.getBalance().add(sum));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(account.getNumber());
        transaction.setCreateDate(Instant.now());
        transaction.setType(TransactionType.REPLENISHMENT);
        transaction.setSum(sum);
        transactionService.save(transaction);
    }

    @Transactional
    @Override
    public void withdraw(String accountNumber, BigDecimal sum) {
        Assert.notNull(accountNumber, "Argument 'accountNumber' must not be null.");
        Assert.notNull(sum, "Argument 'sum' must not be null.");
        Assert.isTrue(sum.compareTo(BigDecimal.ZERO) > 0, "Withdrawal sum must be greater than zero.");

        Account account = get(accountNumber);

        if (account.getBalance().compareTo(sum) >= 0) {
            account.setBalance(account.getBalance().subtract(sum));
            accountRepository.save(account);

            Transaction transaction = new Transaction();
            transaction.setAccountNumber(account.getNumber());
            transaction.setCreateDate(Instant.now());
            transaction.setType(TransactionType.WITHDRAWAL);
            transaction.setSum(sum);
            transactionService.save(transaction);
        }
        else {
            throw new BalanceExceededException(accountNumber, sum);
        }
    }
}
