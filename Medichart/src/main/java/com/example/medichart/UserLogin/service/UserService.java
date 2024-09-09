package com.example.medichart.UserLogin.service;

import com.example.medichart.UserLogin.dto.UserProfileDTO;
import com.example.medichart.UserLogin.entity.UserEntity;
import com.example.medichart.UserLogin.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileDTO getUserProfile(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return convertToDTO(userEntity);
    }

    public void saveUser(UserEntity user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserProfileDTO convertToDTO(UserEntity userEntity) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setEmail(userEntity.getEmail());
        dto.setName(userEntity.getName());
        dto.setGender(userEntity.getGender());
        dto.setDateOfBirth(userEntity.getDateOfBirth());
        dto.setCreatedAt(userEntity.getCreatedAt());
        return dto;
    }
}