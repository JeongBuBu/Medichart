package com.example.medichart.OAuth.service;

import com.example.medichart.OAuth.dto.UserDTO;
import com.example.medichart.OAuth.entity.UserEntity;
import com.example.medichart.OAuth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserProfile(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }

        UserDTO userProfile = new UserDTO();
        userProfile.setUsername(userEntity.getUsername());
        userProfile.setEmail(userEntity.getEmail());
        userProfile.setName(userEntity.getName());
        userProfile.setRole(userEntity.getRole());
        userProfile.setLoginType(userEntity.getLoginType());

        return userProfile;
    }

    public void saveUser(UserEntity user) {
        if (user.getCreatedDate() == null) {
            user.setCreatedDate(LocalDateTime.now());
        }
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}