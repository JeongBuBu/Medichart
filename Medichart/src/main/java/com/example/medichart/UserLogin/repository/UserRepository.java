package com.example.medichart.UserLogin.repository;


import com.example.medichart.UserLogin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}