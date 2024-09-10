package com.example.topups.domain.ports;

import com.example.topups.domain.entities.Login;

import java.util.Optional;

public interface LoginRepository {

    Login save(Login login);

    Optional<Login> findByUsername(String username);
}
