package com.example.medichart.UserLogin.jwt;


import com.example.medichart.JWT.JWTUtil;
import com.example.medichart.UserLogin.dto.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 형식으로 데이터 읽기
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> creds = mapper.readValue(request.getInputStream(), Map.class);

            String email = creds.get("email");
            String password = creds.get("password");

            // 로그 추가
            System.out.println("Attempting authentication for email: " + email);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = customUserDetails.getUsername();

        // 로그 추가
        System.out.println("Successfully authenticated email: " + email);

        // 사용자 권한(Role) 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        // JWT 토큰 생성 (일반 로그인임을 표시)
        String token = jwtUtil.createJwt(email, role, 60 * 60 * 10L, false);  // 일반 로그인임을 나타내기 위해 isSocialLogin = false

        // JWT를 Authorization 헤더에 추가
        response.setHeader("Authorization", "Bearer " + token);

        // 로그 추가
        System.out.println("JWT Token 생성: " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // 로그 추가
        System.out.println("Authentication failed for email: " + request.getParameter("email"));

        // 실패 시 401 상태 코드 반환
        response.setStatus(401);
    }
}