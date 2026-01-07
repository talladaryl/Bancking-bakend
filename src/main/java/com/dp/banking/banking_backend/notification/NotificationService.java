package com.dp.banking.banking_backend.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service de notification pour le pattern Observer
 * Gère les notifications pour les opérations bancaires
 */
@Service
@Slf4j
public class NotificationService {
    
    /**
     * Envoie une notification par email
     */
    public void sendEmailNotification(String email, String subject, String message) {
        log.info("Envoi d'email à {} - Sujet: {} - Message: {}", email, subject, message);
        // Implémentation future : intégration avec un service d'email
    }
    
    /**
     * Envoie une notification SMS
     */
    public void sendSmsNotification(String phoneNumber, String message) {
        log.info("Envoi de SMS au {} - Message: {}", phoneNumber, message);
        // Implémentation future : intégration avec un service SMS
    }
    
    /**
     * Envoie une notification push
     */
    public void sendPushNotification(String userId, String title, String message) {
        log.info("Envoi de notification push à {} - Titre: {} - Message: {}", userId, title, message);
        // Implémentation future : intégration avec un service de notifications push
    }
    
    /**
     * Notifie une opération bancaire
     */
    public void notifyBankingOperation(String accountNumber, String operation, String amount) {
        String message = String.format("Opération %s de %s effectuée sur le compte %s", 
                operation, amount, accountNumber);
        log.info("Notification d'opération bancaire: {}", message);
        
        // Ici on pourrait implémenter la logique pour déterminer
        // quel type de notification envoyer selon les préférences du client
    }
}