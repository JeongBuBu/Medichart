package com.example.medichart.OAuth.controller;


import com.example.medichart.OAuth.dto.UserDTO;
import com.example.medichart.UserLogin.entity.UserEntity;
import com.example.medichart.UserLogin.jwt.JWTUtil;
import com.example.medichart.UserLogin.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
        if ("normal".equals(userDTO.getLoginType())) {
            // 일반 로그인 처리
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
                );
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String token = jwtUtil.createJwt(userDetails.getUsername(), userDetails.getAuthorities().iterator().next().getAuthority(), "normal", 60 * 60 * 10L);
                return ResponseEntity.ok(token);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else if ("social".equals(userDTO.getLoginType())) {
            // 소셜 로그인 처리 (이미 인증된 사용자를 위한 처리)
            UserEntity userEntity = userService.findByUsername(userDTO.getUsername());
            if (userEntity == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
            String token = jwtUtil.createJwt(userEntity.getUsername(), userEntity.getRole(), "social", 60 * 60 * 10L);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid login type");
    }
}
