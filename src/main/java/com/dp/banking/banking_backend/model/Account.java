package com.dp.banking.banking_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le numéro de compte ne peut pas être vide")
    @Size(min = 10, max = 20, message = "Le numéro de compte doit contenir entre 10 et 20 caractères")
    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;
    
    @NotBlank(message = "Le nom du titulaire ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le nom du titulaire doit contenir entre 2 et 100 caractères")
    @Column(name = "account_holder", nullable = false, length = 100)
    private String accountHolder;
    
    @NotNull(message = "Le solde ne peut pas être null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Le solde ne peut pas être négatif")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType = AccountType.CHECKING;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum AccountType {
        CHECKING("Compte courant"),
        SAVINGS("Compte épargne"),
        BUSINESS("Compte professionnel");
        
        private final String displayName;
        
        AccountType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}