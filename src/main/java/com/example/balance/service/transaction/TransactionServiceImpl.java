package com.example.balance.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.balance.model.transaction.Transaction;
import com.example.balance.repository.transaction.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void save(Transaction transaction) {
        Assert.notNull(transaction, "Argument 'transaction' must not be null.");

        transactionRepository.save(transaction);
    }
}
