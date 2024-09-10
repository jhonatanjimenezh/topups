package com.example.topups.application.ports;

import com.example.topups.domain.entities.Login;
import com.example.topups.domain.entities.User;

public interface UserUseCase {
    void registerUser(User user, Login login);
}
