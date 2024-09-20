/*
package com.example.medichart.UserLogin.controller;


import com.example.medichart.UserLogin.dto.JwtResponse;
import com.example.medichart.UserLogin.dto.LoginRequest;
import com.example.medichart.UserLogin.jwt.JWTUtil;
import com.example.medichart.UserLogin.service.JoinService;
import com.example.medichart.UserLogin.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000") // 클라이언트 도메인에 맞게 설정
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = loginService.authenticate(loginRequest);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("로그인 실패: " + e.getMessage());
        }
    }
}*/
