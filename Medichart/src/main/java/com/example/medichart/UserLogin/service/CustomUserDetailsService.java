package com.example.medichart.UserLogin.service;


import com.example.medichart.UserLogin.dto.CustomUserDetails;
import com.example.medichart.UserLogin.entity.NormalUserEntity;
import com.example.medichart.UserLogin.repository.NormalUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final NormalUserRepository normalUserRepository;

    public CustomUserDetailsService(NormalUserRepository normalUserRepository) {
        this.normalUserRepository = normalUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Optional<UserEntity>를 반환받아 처리
        NormalUserEntity userData = normalUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(userData);
    }
}