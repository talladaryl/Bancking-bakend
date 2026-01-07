package com.dp.banking.banking_backend.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountNumberGenerator {
    
    private static final String COUNTRY_CODE = "FR";
    private static final String BANK_CODE = "30001";
    private static final String BRANCH_CODE = "00794";
    private static final SecureRandom random = new SecureRandom();
    
    /**
     * Génère un numéro de compte bancaire français (IBAN)
     * Format: FR76 3000 1007 94XX XXXX XXXX XX
     */
    public String generateAccountNumber() {
        // Génération de 11 chiffres aléatoires pour la partie compte
        StringBuilder accountPart = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            accountPart.append(random.nextInt(10));
        }
        
        // Construction du numéro de compte de base
        String baseAccount = BANK_CODE + BRANCH_CODE + accountPart.toString();
        
        // Calcul de la clé RIB (simplifié)
        int key = calculateRibKey(BANK_CODE, BRANCH_CODE, accountPart.toString());
        
        // Construction de l'IBAN complet
        String ibanWithoutKey = COUNTRY_CODE + "00" + baseAccount + String.format("%02d", key);
        int ibanKey = calculateIbanKey(ibanWithoutKey);
        
        return COUNTRY_CODE + String.format("%02d", ibanKey) + baseAccount + String.format("%02d", key);
    }
    
    private int calculateRibKey(String bankCode, String branchCode, String accountNumber) {
        // Calcul simplifié de la clé RIB
        long sum = 0;
        String fullNumber = bankCode + branchCode + accountNumber;
        
        for (int i = 0; i < fullNumber.length(); i++) {
            sum += Character.getNumericValue(fullNumber.charAt(i)) * (i + 1);
        }
        
        return (int) (97 - (sum % 97));
    }
    
    private int calculateIbanKey(String iban) {
        // Déplacement des 4 premiers caractères à la fin
        String rearranged = iban.substring(4) + iban.substring(0, 4);
        
        // Remplacement des lettres par des chiffres (A=10, B=11, etc.)
        StringBuilder numeric = new StringBuilder();
        for (char c : rearranged.toCharArray()) {
            if (Character.isLetter(c)) {
                numeric.append(c - 'A' + 10);
            } else {
                numeric.append(c);
            }
        }
        
        // Calcul du modulo 97
        long remainder = 0;
        for (char digit : numeric.toString().toCharArray()) {
            remainder = (remainder * 10 + Character.getNumericValue(digit)) % 97;
        }
        
        return (int) (98 - remainder);
    }
}