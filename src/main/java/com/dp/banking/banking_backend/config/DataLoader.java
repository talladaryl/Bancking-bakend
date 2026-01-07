package com.dp.banking.banking_backend.config;

import com.dp.banking.banking_backend.model.Account;
import com.dp.banking.banking_backend.model.Operator;
import com.dp.banking.banking_backend.repository.AccountRepository;
import com.dp.banking.banking_backend.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final OperatorRepository operatorRepository;
    private final AccountRepository accountRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (operatorRepository.count() == 0) {
            loadOperators();
        }
        
        if (accountRepository.count() == 0) {
            loadAccounts();
        }
        
        log.info("Données de test chargées avec succès !");
    }
    
    private void loadOperators() {
        log.info("Chargement des opérateurs de test...");
        
        Operator operator1 = new Operator();
        operator1.setName("Jean Dupont");
        operator1.setEmail("jean.dupont@banking.com");
        operator1.setPhone("+33 1 23 45 67 89");
        operator1.setIsActive(true);
        
        Operator operator2 = new Operator();
        operator2.setName("Marie Martin");
        operator2.setEmail("marie.martin@banking.com");
        operator2.setPhone("+33 1 98 76 54 32");
        operator2.setIsActive(true);
        
        Operator operator3 = new Operator();
        operator3.setName("Pierre Durand");
        operator3.setEmail("pierre.durand@banking.com");
        operator3.setPhone("+33 1 11 22 33 44");
        operator3.setIsActive(true);
        
        operatorRepository.save(operator1);
        operatorRepository.save(operator2);
        operatorRepository.save(operator3);
        
        log.info("3 opérateurs créés avec succès");
    }
    
    private void loadAccounts() {
        log.info("Chargement des comptes de test...");
        
        Account account1 = new Account();
        account1.setAccountNumber("FR7630001007941234567890185");
        account1.setAccountHolder("Alice Dubois");
        account1.setBalance(new BigDecimal("1500.00"));
        account1.setAccountType(Account.AccountType.CHECKING);
        account1.setCreatedAt(LocalDateTime.now());
        account1.setUpdatedAt(LocalDateTime.now());
        account1.setIsActive(true);
        
        Account account2 = new Account();
        account2.setAccountNumber("FR7630001007941234567890186");
        account2.setAccountHolder("Bob Leroy");
        account2.setBalance(new BigDecimal("2750.50"));
        account2.setAccountType(Account.AccountType.SAVINGS);
        account2.setCreatedAt(LocalDateTime.now());
        account2.setUpdatedAt(LocalDateTime.now());
        account2.setIsActive(true);
        
        Account account3 = new Account();
        account3.setAccountNumber("FR7630001007941234567890187");
        account3.setAccountHolder("Claire Moreau");
        account3.setBalance(new BigDecimal("5000.00"));
        account3.setAccountType(Account.AccountType.BUSINESS);
        account3.setCreatedAt(LocalDateTime.now());
        account3.setUpdatedAt(LocalDateTime.now());
        account3.setIsActive(true);
        
        Account account4 = new Account();
        account4.setAccountNumber("FR7630001007941234567890188");
        account4.setAccountHolder("David Bernard");
        account4.setBalance(new BigDecimal("850.25"));
        account4.setAccountType(Account.AccountType.CHECKING);
        account4.setCreatedAt(LocalDateTime.now());
        account4.setUpdatedAt(LocalDateTime.now());
        account4.setIsActive(true);
        
        Account account5 = new Account();
        account5.setAccountNumber("FR7630001007941234567890189");
        account5.setAccountHolder("Emma Petit");
        account5.setBalance(new BigDecimal("12000.00"));
        account5.setAccountType(Account.AccountType.SAVINGS);
        account5.setCreatedAt(LocalDateTime.now());
        account5.setUpdatedAt(LocalDateTime.now());
        account5.setIsActive(true);
        
        Account account6 = new Account();
        account6.setAccountNumber("FR7630001007941234567890190");
        account6.setAccountHolder("François Roux");
        account6.setBalance(new BigDecimal("3200.75"));
        account6.setAccountType(Account.AccountType.BUSINESS);
        account6.setCreatedAt(LocalDateTime.now());
        account6.setUpdatedAt(LocalDateTime.now());
        account6.setIsActive(true);
        
        accountRepository.save(account1);
        accountRepository.save(account2);
        accountRepository.save(account3);
        accountRepository.save(account4);
        accountRepository.save(account5);
        accountRepository.save(account6);
        
        log.info("6 comptes créés avec succès");
    }
}