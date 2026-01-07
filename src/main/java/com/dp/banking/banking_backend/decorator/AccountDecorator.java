package com.dp.banking.banking_backend.decorator;

import com.dp.banking.banking_backend.model.Account;

import java.math.BigDecimal;

/**
 * Interface de base pour le pattern Decorator
 * Permet d'ajouter des fonctionnalités aux comptes bancaires
 */
public interface AccountDecorator {
    
    /**
     * Calcule les intérêts pour un compte
     */
    BigDecimal calculateInterest(Account account);
    
    /**
     * Calcule les frais pour un compte
     */
    BigDecimal calculateFees(Account account);
    
    /**
     * Vérifie si une opération est autorisée
     */
    boolean isOperationAllowed(Account account, BigDecimal amount, String operationType);
    
    /**
     * Applique des règles métier spécifiques au type de compte
     */
    Account applyBusinessRules(Account account);
}