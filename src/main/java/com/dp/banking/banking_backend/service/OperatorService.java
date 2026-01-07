package com.dp.banking.banking_backend.service;

import com.dp.banking.banking_backend.model.Operator;
import com.dp.banking.banking_backend.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OperatorService {
    
    private final OperatorRepository operatorRepository;
    
    public List<Operator> findAll() {
        log.debug("Récupération de tous les opérateurs");
        return operatorRepository.findAll();
    }
    
    public List<Operator> findActiveOperators() {
        log.debug("Récupération des opérateurs actifs");
        return operatorRepository.findByIsActiveTrue();
    }
    
    public Optional<Operator> findById(Long id) {
        log.debug("Recherche de l'opérateur avec l'ID: {}", id);
        return operatorRepository.findById(id);
    }
    
    public Optional<Operator> findByEmail(String email) {
        log.debug("Recherche de l'opérateur avec l'email: {}", email);
        return operatorRepository.findByEmail(email);
    }
    
    public Operator save(Operator operator) {
        log.info("Sauvegarde de l'opérateur: {}", operator.getName());
        if (operatorRepository.existsByEmail(operator.getEmail())) {
            throw new IllegalArgumentException("Un opérateur avec cet email existe déjà");
        }
        return operatorRepository.save(operator);
    }
    
    public Operator update(Long id, Operator operator) {
        log.info("Mise à jour de l'opérateur avec l'ID: {}", id);
        return operatorRepository.findById(id)
                .map(existingOperator -> {
                    existingOperator.setName(operator.getName());
                    existingOperator.setEmail(operator.getEmail());
                    existingOperator.setPhone(operator.getPhone());
                    existingOperator.setIsActive(operator.getIsActive());
                    return operatorRepository.save(existingOperator);
                })
                .orElseThrow(() -> new IllegalArgumentException("Opérateur non trouvé avec l'ID: " + id));
    }
    
    public void deleteById(Long id) {
        log.info("Suppression de l'opérateur avec l'ID: {}", id);
        if (!operatorRepository.existsById(id)) {
            throw new IllegalArgumentException("Opérateur non trouvé avec l'ID: " + id);
        }
        operatorRepository.deleteById(id);
    }
    
    public List<Operator> searchByName(String name) {
        log.debug("Recherche d'opérateurs par nom: {}", name);
        return operatorRepository.findByNameContaining(name);
    }
}