package com.example.topups.domain.ports;

import com.example.topups.domain.entities.Operator;

import java.util.List;
import java.util.Optional;

public interface OperatorRepository {

    Operator save(Operator operator);
    Optional<Operator> findById(Long id);
    List<Operator> findAll();
}
