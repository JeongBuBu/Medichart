package com.example.medichart.UserLogin.controller;


import com.example.medichart.UserLogin.dto.JoinDTO;
import com.example.medichart.UserLogin.dto.LoginRequest;
import com.example.medichart.UserLogin.dto.LoginResponse;
import com.example.medichart.UserLogin.repository.NormalUserRepository;
import com.example.medichart.UserLogin.service.JoinService;
import com.example.medichart.UserLogin.service.LoginService;
import com.example.medichart.UserLogin.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final LoginService loginService;
    private final JoinService joinService;
    private final NormalUserRepository normalUserRepository;

    public AuthController(LoginService loginService, JoinService joinService,
                          NormalUserRepository normalUserRepository) {
        this.loginService = loginService;
        this.joinService = joinService;
        this.normalUserRepository = normalUserRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = loginService.authenticate(loginRequest);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, "Authentication failed"));
        }
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDTO joinDTO) {
        try {
            joinService.joinProcess(joinDTO);
            return ResponseEntity.ok("회원가입이 완료되었습니다!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입에 실패했습니다.");
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailAvailability(@RequestParam("email") String email) {
        boolean isTaken = normalUserRepository.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isTaken", isTaken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // 로그아웃 구현 (JWT 무효화 등)
        return ResponseEntity.ok().build();
    }
}