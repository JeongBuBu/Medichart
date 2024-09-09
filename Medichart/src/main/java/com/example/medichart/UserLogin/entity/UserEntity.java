package com.example.medichart.UserLogin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email; // 이메일 필드로 변경
    private String password;
    private String role;

    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt = LocalDateTime.now();  // 현재 시간으로 기본값 설정된 생성 날짜 필드
}