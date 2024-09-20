package com.example.medichart.UserLogin.service;

import com.example.medichart.UserLogin.dto.UserProfileDTO;
import com.example.medichart.UserLogin.entity.NormalUserEntity;
import com.example.medichart.UserLogin.repository.NormalUserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final NormalUserRepository normalUserRepository;

    public UserService(NormalUserRepository normalUserRepository) {
        this.normalUserRepository = normalUserRepository;
    }

    public UserProfileDTO getUserProfile(String email) {
        NormalUserEntity normalUserEntity = normalUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        UserProfileDTO dto = new UserProfileDTO();
        dto.setEmail(normalUserEntity.getEmail());
        dto.setName(normalUserEntity.getName());
        dto.setGender(normalUserEntity.getGender());
        dto.setDateOfBirth(normalUserEntity.getDateOfBirth()); // 확인
        dto.setCreatedAt(normalUserEntity.getCreatedAt());

        return dto;
    }

    public void saveUser(NormalUserEntity user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }
        normalUserRepository.save(user);
    }

    @Transactional(readOnly = true)
    public NormalUserEntity findByEmail(String email) {
        return normalUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}