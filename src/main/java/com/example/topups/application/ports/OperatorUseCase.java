package com.example.topups.application.ports;

import com.example.topups.domain.entities.Operator;

import java.util.List;

public interface OperatorUseCase {

    void registerOperator(Operator operator);
    List<Operator> getAllOperators();
}
