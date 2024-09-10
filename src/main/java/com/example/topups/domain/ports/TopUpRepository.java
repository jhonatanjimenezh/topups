package com.example.topups.domain.ports;

import com.example.topups.domain.entities.TopUp;

import java.util.List;
import java.util.Optional;

public interface TopUpRepository {

    TopUp save(TopUp topUp);
    Optional<TopUp> findById(Long id);          // Find a TopUp by its ID
    List<TopUp> findByUserId(Long userId);      // Find all TopUps by User ID
    List<TopUp> findByOperatorId(Long operatorId); // Find all TopUps by Operator ID
    List<TopUp> findAll();                      // Retrieve all TopUps
}
