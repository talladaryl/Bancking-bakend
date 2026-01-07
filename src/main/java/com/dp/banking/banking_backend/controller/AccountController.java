package com.dp.banking.banking_backend.controller;

import com.dp.banking.banking_backend.model.Account;
import com.dp.banking.banking_backend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "API de gestion des comptes bancaires")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    
    private final AccountService accountService;
    
    @GetMapping
    @Operation(summary = "Récupérer tous les comptes", description = "Retourne la liste de tous les comptes")
    @ApiResponse(responseCode = "200", description = "Liste des comptes récupérée avec succès")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/active")
    @Operation(summary = "Récupérer les comptes actifs", description = "Retourne la liste des comptes actifs")
    @ApiResponse(responseCode = "200", description = "Liste des comptes actifs récupérée avec succès")
    public ResponseEntity<List<Account>> getActiveAccounts() {
        List<Account> accounts = accountService.findActiveAccounts();
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un compte par ID", description = "Retourne un compte spécifique par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compte trouvé"),
        @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<Account> getAccountById(
            @Parameter(description = "ID du compte") @PathVariable Long id) {
        return accountService.findById(id)
                .map(account -> ResponseEntity.ok(account))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/number/{accountNumber}")
    @Operation(summary = "Récupérer un compte par numéro", description = "Retourne un compte par son numéro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compte trouvé"),
        @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<Account> getAccountByNumber(
            @Parameter(description = "Numéro du compte") @PathVariable String accountNumber) {
        return accountService.findByAccountNumber(accountNumber)
                .map(account -> ResponseEntity.ok(account))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau compte", description = "Crée un nouveau compte bancaire")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Compte créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        try {
            Account savedAccount = accountService.save(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un compte", description = "Met à jour un compte existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compte mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Compte non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Account> updateAccount(
            @Parameter(description = "ID du compte") @PathVariable Long id,
            @Valid @RequestBody Account account) {
        try {
            Account updatedAccount = accountService.update(id, account);
            return ResponseEntity.ok(updatedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un compte", description = "Supprime un compte par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Compte supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "ID du compte") @PathVariable Long id) {
        try {
            accountService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/deposit")
    @Operation(summary = "Effectuer un dépôt", description = "Effectue un dépôt sur un compte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dépôt effectué avec succès"),
        @ApiResponse(responseCode = "404", description = "Compte non trouvé"),
        @ApiResponse(responseCode = "400", description = "Montant invalide")
    })
    public ResponseEntity<Account> deposit(
            @Parameter(description = "ID du compte") @PathVariable Long id,
            @RequestBody Map<String, BigDecimal> request) {
        try {
            BigDecimal amount = request.get("amount");
            Account updatedAccount = accountService.deposit(id, amount);
            return ResponseEntity.ok(updatedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/withdraw")
    @Operation(summary = "Effectuer un retrait", description = "Effectue un retrait sur un compte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrait effectué avec succès"),
        @ApiResponse(responseCode = "404", description = "Compte non trouvé"),
        @ApiResponse(responseCode = "400", description = "Montant invalide ou solde insuffisant")
    })
    public ResponseEntity<Account> withdraw(
            @Parameter(description = "ID du compte") @PathVariable Long id,
            @RequestBody Map<String, BigDecimal> request) {
        try {
            BigDecimal amount = request.get("amount");
            Account updatedAccount = accountService.withdraw(id, amount);
            return ResponseEntity.ok(updatedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}