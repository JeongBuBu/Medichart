package com.example.medichart.JWT;


import com.example.medichart.OAuth.dto.CustomOAuth2User;
import com.example.medichart.OAuth.dto.UserDTO;
import com.example.medichart.UserLogin.dto.CustomUserDetails;
import com.example.medichart.UserLogin.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JWTFilter(JWTUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Authorization 헤더에서 JWT 토큰을 추출
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 Bearer로 시작하지 않으면 필터 체인 계속 진행
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Bearer 토큰 값 추출
        String token = authorization.split(" ")[1];

        // 토큰이 만료되었는지 확인
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 소셜 로그인 여부 판단 (토큰의 필드명에 따라 달라짐)
        boolean isSocialLogin = jwtUtil.getUsernameOrEmail(token, false) != null;

        if (isSocialLogin) {
            // 소셜 로그인 처리 (username 필드를 사용)
            String username = jwtUtil.getUsernameOrEmail(token, false);  // 소셜 로그인 시 username 추출
            String role = jwtUtil.getRole(token);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setRole(role);

            CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            // 일반 로그인 처리 (email 필드를 사용)
            String email = jwtUtil.getUsernameOrEmail(token, true);  // 일반 로그인 시 email 추출

            CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);

            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails,
                    null,
                    customUserDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}