package com.dp.banking.banking_backend.repository;

import com.dp.banking.banking_backend.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
    
    Optional<Operator> findByEmail(String email);
    
    List<Operator> findByIsActiveTrue();
    
    @Query("SELECT o FROM Operator o WHERE o.name LIKE %:name%")
    List<Operator> findByNameContaining(String name);
    
    boolean existsByEmail(String email);
}