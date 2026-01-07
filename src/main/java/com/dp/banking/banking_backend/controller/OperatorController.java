package com.dp.banking.banking_backend.controller;

import com.dp.banking.banking_backend.model.Operator;
import com.dp.banking.banking_backend.service.OperatorService;
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

import java.util.List;

@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
@Tag(name = "Operators", description = "API de gestion des opérateurs bancaires")
@CrossOrigin(origins = "http://localhost:3000")
public class OperatorController {
    
    private final OperatorService operatorService;
    
    @GetMapping
    @Operation(summary = "Récupérer tous les opérateurs", description = "Retourne la liste de tous les opérateurs")
    @ApiResponse(responseCode = "200", description = "Liste des opérateurs récupérée avec succès")
    public ResponseEntity<List<Operator>> getAllOperators() {
        List<Operator> operators = operatorService.findAll();
        return ResponseEntity.ok(operators);
    }
    
    @GetMapping("/active")
    @Operation(summary = "Récupérer les opérateurs actifs", description = "Retourne la liste des opérateurs actifs")
    @ApiResponse(responseCode = "200", description = "Liste des opérateurs actifs récupérée avec succès")
    public ResponseEntity<List<Operator>> getActiveOperators() {
        List<Operator> operators = operatorService.findActiveOperators();
        return ResponseEntity.ok(operators);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un opérateur par ID", description = "Retourne un opérateur spécifique par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Opérateur trouvé"),
        @ApiResponse(responseCode = "404", description = "Opérateur non trouvé")
    })
    public ResponseEntity<Operator> getOperatorById(
            @Parameter(description = "ID de l'opérateur") @PathVariable Long id) {
        return operatorService.findById(id)
                .map(operator -> ResponseEntity.ok(operator))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouvel opérateur", description = "Crée un nouvel opérateur bancaire")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Opérateur créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Operator> createOperator(@Valid @RequestBody Operator operator) {
        try {
            Operator savedOperator = operatorService.save(operator);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOperator);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un opérateur", description = "Met à jour un opérateur existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Opérateur mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Opérateur non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Operator> updateOperator(
            @Parameter(description = "ID de l'opérateur") @PathVariable Long id,
            @Valid @RequestBody Operator operator) {
        try {
            Operator updatedOperator = operatorService.update(id, operator);
            return ResponseEntity.ok(updatedOperator);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un opérateur", description = "Supprime un opérateur par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Opérateur supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Opérateur non trouvé")
    })
    public ResponseEntity<Void> deleteOperator(
            @Parameter(description = "ID de l'opérateur") @PathVariable Long id) {
        try {
            operatorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Rechercher des opérateurs par nom", description = "Recherche des opérateurs par nom")
    @ApiResponse(responseCode = "200", description = "Résultats de recherche récupérés avec succès")
    public ResponseEntity<List<Operator>> searchOperatorsByName(
            @Parameter(description = "Nom à rechercher") @RequestParam String name) {
        List<Operator> operators = operatorService.searchByName(name);
        return ResponseEntity.ok(operators);
    }
}