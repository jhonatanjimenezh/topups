package com.example.topups.infrastructure.adapters.repository;

import com.example.topups.domain.entities.TopUp;
import com.example.topups.domain.ports.TopUpRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTopUpRepository extends TopUpRepository, JpaRepository<TopUp, Long> {
    List<TopUp> findByUserId(Long userId);
    List<TopUp> findByOperatorId(Long operatorId);
}
