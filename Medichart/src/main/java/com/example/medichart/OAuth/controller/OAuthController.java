package com.example.medichart.OAuth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naverRedirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @GetMapping("/urls")
    public Map<String, String> getOAuthUrls() {
        String naverUrl = String.format("https://nid.naver.com/oauth2.0/authorize?client_id=%s&response_type=code&redirect_uri=%s", naverClientId, naverRedirectUri);
        String kakaoUrl = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=profile_nickname,account_email", kakaoClientId, kakaoRedirectUri);
        String googleUrl = String.format("https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=%s&redirect_uri=%s&scope=openid email profile", googleClientId, googleRedirectUri);

        return Map.of(
                "naver", naverUrl,
                "kakao", kakaoUrl,
                "google", googleUrl
        );
    }
}