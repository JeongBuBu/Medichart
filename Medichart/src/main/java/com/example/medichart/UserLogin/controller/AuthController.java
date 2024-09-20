package com.example.medichart.UserLogin.controller;


import com.example.medichart.UserLogin.dto.JoinDTO;
import com.example.medichart.UserLogin.dto.LoginRequest;
import com.example.medichart.UserLogin.dto.LoginResponse;
import com.example.medichart.UserLogin.repository.NormalUserRepository;
import com.example.medichart.UserLogin.service.JoinService;
import com.example.medichart.UserLogin.service.LoginService;
import com.example.medichart.UserLogin.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        // JWT 토큰 무효화 (필요하다면)
        // 예를 들어, Redis 또는 데이터베이스에 저장된 블랙리스트를 사용하여 토큰 무효화 로직 추가

        // 쿠키 삭제 (필요한 경우)
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 만료
        response.addCookie(cookie);

        return ResponseEntity.ok().build(); // 로그아웃 성공 응답
    }
}