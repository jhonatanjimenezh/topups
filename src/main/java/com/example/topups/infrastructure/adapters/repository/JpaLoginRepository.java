package com.example.topups.infrastructure.adapters.repository;

import com.example.topups.domain.entities.Login;
import com.example.topups.domain.ports.LoginRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLoginRepository extends LoginRepository, JpaRepository<Login, Long> {
    Optional<Login> findByUsername(String username);
}
