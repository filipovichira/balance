package com.example.balance.repository.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.balance.model.transaction.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
