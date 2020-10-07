package com.example.balance.repository.account;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.balance.model.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ac FROM Account ac WHERE ac.number = :number")
    Optional<Account> findByNumberForWrite(@Param("number") String number);

    Optional<Account> findByNumber(String number);
}
