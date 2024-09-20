package com.example.medichart.OAuth.oauth2;

import com.example.medichart.JWT.JWTUtil;
import com.example.medichart.OAuth.dto.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    public CustomSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? "ROLE_USER" : authorities.iterator().next().getAuthority();

        String token = jwtUtil.createJwt(username, role, 60 * 60 * 60 * 60L, true); // 소셜 로그인 표시

        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS에서만 전송
        cookie.setPath("/");
        cookie.setMaxAge(3600); // 1시간
        response.addCookie(cookie);

        // 디버깅을 위한 로그 추가
        System.out.println("JWT: " + token);
        System.out.println("Cookie: " + cookie.getValue());

        // 리다이렉션 처리 (프론트엔드 페이지 URL로 수정)
        response.sendRedirect("http://localhost:3000/");
    }
}
