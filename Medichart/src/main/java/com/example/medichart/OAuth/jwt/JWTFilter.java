package com.example.springjwt.jwt;

import com.example.medichart.OAuth.dto.CustomOAuth2User;
import com.example.medichart.OAuth.dto.CustomUserDetails;
import com.example.medichart.OAuth.dto.UserDTO;
import com.example.medichart.OAuth.entity.UserEntity;
import com.example.medichart.OAuth.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;

        // 쿠키 또는 헤더에서 토큰을 추출
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    authorization = cookie.getValue();
                    break;
                }
            }
        }

        if (authorization == null) {
            authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith("Bearer ")) {
                authorization = authorization.split(" ")[1];
            }
        }

        // Authorization 검증
        if (authorization == null) {
            System.out.println("Token is null");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 검증
        String token = authorization;
        if (jwtUtil.isExpired(token)) {
            System.out.println("Token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        // Token에서 username, role, loginType 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        String loginType = jwtUtil.getLoginType(token);

        Authentication authToken;

        // 로그인 타입에 따라 적절한 UserDetails 사용
        if ("social".equals(loginType)) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setRole(role);
            userDTO.setLoginType(loginType);
            CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
            authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setRole(role);
            CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
            authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        }

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
