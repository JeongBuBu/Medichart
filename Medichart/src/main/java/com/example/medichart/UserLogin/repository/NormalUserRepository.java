package com.example.medichart.UserLogin.repository;


import com.example.medichart.UserLogin.entity.NormalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NormalUserRepository extends JpaRepository<NormalUserEntity, Integer> {

    Boolean existsByEmail(String email);

    Optional<NormalUserEntity> findByEmail(String email);
}