package com.example.topups.infrastructure.adapters.repository;

import com.example.topups.domain.entities.User;
import com.example.topups.domain.ports.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends UserRepository, JpaRepository<User, Long> {

}
