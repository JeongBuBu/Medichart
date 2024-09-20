package com.example.medichart.UserLogin.service;


import com.example.medichart.UserLogin.dto.JoinDTO;
import com.example.medichart.UserLogin.entity.NormalUserEntity;
import com.example.medichart.UserLogin.repository.NormalUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final NormalUserRepository normalUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(NormalUserRepository normalUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.normalUserRepository = normalUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {
        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        String confirmPassword = joinDTO.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (normalUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        NormalUserEntity normalUserEntity = new NormalUserEntity();
        normalUserEntity.setEmail(email);
        normalUserEntity.setPassword(bCryptPasswordEncoder.encode(password));
        normalUserEntity.setRole("ROLE_USER");
        normalUserEntity.setName(joinDTO.getName());
        normalUserEntity.setGender(joinDTO.getGender());
        normalUserEntity.setDateOfBirth(joinDTO.getDateOfBirth());

        normalUserRepository.save(normalUserEntity);
    }
}
