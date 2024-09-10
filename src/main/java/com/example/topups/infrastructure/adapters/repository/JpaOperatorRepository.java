package com.example.topups.infrastructure.adapters.repository;

import com.example.topups.domain.entities.Operator;
import com.example.topups.domain.ports.OperatorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOperatorRepository extends OperatorRepository, JpaRepository<Operator, Long> {

}
