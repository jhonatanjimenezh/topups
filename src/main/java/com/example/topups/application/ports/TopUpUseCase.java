package com.example.topups.application.ports;

import com.example.topups.domain.entities.TopUp;

import java.util.List;

public interface TopUpUseCase {
    void performTopUp(TopUp topUp);
    List<TopUp> getTopUpsByUser(Long userId);
    List<TopUp> getTopUpsByOperator(Long operatorId);
    List<TopUp> getAllTopUps();
}
