package com.example.topups.domain.ports;

import com.example.topups.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
    List<User> findAll();
}
