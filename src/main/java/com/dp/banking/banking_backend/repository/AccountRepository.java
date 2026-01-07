package com.dp.banking.banking_backend.repository;

import com.dp.banking.banking_backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    List<Account> findByIsActiveTrue();
    
    List<Account> findByAccountHolder(String accountHolder);
    
    @Query("SELECT a FROM Account a WHERE a.balance >= :minBalance")
    List<Account> findByBalanceGreaterThanEqual(BigDecimal minBalance);
    
    @Query("SELECT a FROM Account a WHERE a.accountType = :accountType AND a.isActive = true")
    List<Account> findByAccountTypeAndIsActiveTrue(Account.AccountType accountType);
    
    boolean existsByAccountNumber(String accountNumber);
}