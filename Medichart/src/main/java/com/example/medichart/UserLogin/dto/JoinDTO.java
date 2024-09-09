package com.example.medichart.UserLogin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class JoinDTO {

    private String email;
    private String password;
    private String confirmPassword; // 비밀번호 확인 필드 추가
    private String name; // 사용자 이름 필드 추가
    private String gender; // 성별 필드 추가
    private LocalDate dateOfBirth; // 생년월일 필드 추가
    private LocalDateTime createdAt; // 필요하다면 생성 날짜 필드 추가
}