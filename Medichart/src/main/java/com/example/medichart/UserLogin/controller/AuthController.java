package com.example.medichart.UserLogin.controller;


import com.example.medichart.UserLogin.dto.*;
import com.example.medichart.UserLogin.entity.UserEntity;
import com.example.medichart.UserLogin.jwt.JWTUtil;
import com.example.medichart.UserLogin.service.JoinService;
import com.example.medichart.UserLogin.service.LoginService;
import com.example.medichart.UserLogin.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final JoinService joinService;
    private final LoginService loginService;

    public AuthController(JWTUtil jwtUtil, UserService userService, JoinService joinService, LoginService loginService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.joinService = joinService;
        this.loginService = loginService;
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
            return new ResponseEntity<>("회원가입이 완료되었습니다!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("회원가입에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailAvailability(@RequestParam("email") String email) {
        boolean isTaken = userService.isEmailTaken(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isTaken", isTaken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // 로그아웃 구현 (JWT 무효화 등)
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        try {
            UserProfileDTO profile = userService.getUserProfile(userDetails.getUsername());
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<UserEntity> getUserInfo(@RequestParam String email) {
        UserEntity user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }
}