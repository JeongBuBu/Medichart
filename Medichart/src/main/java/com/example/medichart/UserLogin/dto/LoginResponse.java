package com.example.medichart.UserLogin.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private String message;

    // 기본 생성자
    public LoginResponse() {
    }

    // 토큰만 전달하는 생성자
    public LoginResponse(String token) {
        this.token = token;
    }

    // 메시지만 전달하는 생성자
    // public LoginResponse(String message) { ... } // 이 생성자는 제거합니다.

    // 토큰과 메시지를 모두 전달하는 생성자 (필요 시 추가)
    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
}