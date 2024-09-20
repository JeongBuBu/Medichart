package com.example.medichart.OAuth.service;

import com.example.medichart.OAuth.dto.UserDTO;
import com.example.medichart.OAuth.entity.SocialUserEntity;
import com.example.medichart.OAuth.exception.UserNotFoundException;
import com.example.medichart.OAuth.repository.SocialUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SocialUserService {

    private final SocialUserRepository socialUserRepository;

    public SocialUserService(SocialUserRepository socialUserRepository) {
        this.socialUserRepository = socialUserRepository;
    }

    public UserDTO getUserProfile(String username) {
        SocialUserEntity socialUserEntity = socialUserRepository.findByUsername(username);
        if (socialUserEntity == null) {
            throw new UserNotFoundException("User with username " + username + " not found");
        }

        UserDTO userProfile = new UserDTO();
        userProfile.setUsername(socialUserEntity.getUsername());
        userProfile.setEmail(socialUserEntity.getEmail());
        userProfile.setName(socialUserEntity.getName());
        userProfile.setRole(socialUserEntity.getRole());
        userProfile.setLoginType(socialUserEntity.getLoginType());

        return userProfile;
    }

    public void saveUser(SocialUserEntity user) {
        if (user.getCreatedDate() == null) {
            user.setCreatedDate(LocalDateTime.now());
        }
        socialUserRepository.save(user);
    }

    @Transactional(readOnly = true)
    public SocialUserEntity findByUsername(String username) {
        return socialUserRepository.findByUsername(username);
    }
}