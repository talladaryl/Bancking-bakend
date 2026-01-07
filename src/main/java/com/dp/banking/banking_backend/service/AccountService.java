package com.dp.banking.banking_backend.service;

import com.dp.banking.banking_backend.model.Account;
import com.dp.banking.banking_backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountService {
    
    private final AccountRepository accountRepository;
    
    public List<Account> findAll() {
        log.debug("Récupération de tous les comptes");
        return accountRepository.findAll();
    }
    
    public List<Account> findActiveAccounts() {
        log.debug("Récupération des comptes actifs");
        return accountRepository.findByIsActiveTrue();
    }
    
    public Optional<Account> findById(Long id) {
        log.debug("Recherche du compte avec l'ID: {}", id);
        return accountRepository.findById(id);
    }
    
    public Optional<Account> findByAccountNumber(String accountNumber) {
        log.debug("Recherche du compte avec le numéro: {}", accountNumber);
        return accountRepository.findByAccountNumber(accountNumber);
    }
    
    public Account save(Account account) {
        log.info("Sauvegarde du compte: {}", account.getAccountNumber());
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new IllegalArgumentException("Un compte avec ce numéro existe déjà");
        }
        return accountRepository.save(account);
    }
    
    public Account update(Long id, Account account) {
        log.info("Mise à jour du compte avec l'ID: {}", id);
        return accountRepository.findById(id)
                .map(existingAccount -> {
                    existingAccount.setAccountHolder(account.getAccountHolder());
                    existingAccount.setBalance(account.getBalance());
                    existingAccount.setAccountType(account.getAccountType());
                    existingAccount.setIsActive(account.getIsActive());
                    return accountRepository.save(existingAccount);
                })
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé avec l'ID: " + id));
    }
    
    public void deleteById(Long id) {
        log.info("Suppression du compte avec l'ID: {}", id);
        if (!accountRepository.existsById(id)) {
            throw new IllegalArgumentException("Compte non trouvé avec l'ID: " + id);
        }
        accountRepository.deleteById(id);
    }
    
    public List<Account> findByAccountHolder(String accountHolder) {
        log.debug("Recherche de comptes par titulaire: {}", accountHolder);
        return accountRepository.findByAccountHolder(accountHolder);
    }
    
    public List<Account> findByMinimumBalance(BigDecimal minBalance) {
        log.debug("Recherche de comptes avec solde minimum: {}", minBalance);
        return accountRepository.findByBalanceGreaterThanEqual(minBalance);
    }
    
    public Account deposit(Long accountId, BigDecimal amount) {
        log.info("Dépôt de {} sur le compte ID: {}", amount, accountId);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du dépôt doit être positif");
        }
        
        return accountRepository.findById(accountId)
                .map(account -> {
                    account.setBalance(account.getBalance().add(amount));
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé avec l'ID: " + accountId));
    }
    
    public Account withdraw(Long accountId, BigDecimal amount) {
        log.info("Retrait de {} du compte ID: {}", amount, accountId);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du retrait doit être positif");
        }
        
        return accountRepository.findById(accountId)
                .map(account -> {
                    if (account.getBalance().compareTo(amount) < 0) {
                        throw new IllegalArgumentException("Solde insuffisant");
                    }
                    account.setBalance(account.getBalance().subtract(amount));
                    return accountRepository.save(account);
                })
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé avec l'ID: " + accountId));
    }
}