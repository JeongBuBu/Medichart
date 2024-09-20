package com.example.medichart.OAuth.service;

import com.example.medichart.OAuth.dto.GoogleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class GoogleOAuth2Service {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String accessTokenUri;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String userInfoUri;

    private final RestTemplate restTemplate;

    public GoogleOAuth2Service(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GoogleResponse getUserInfo(String code) {
        String accessToken = getAccessToken(code);

        String url = UriComponentsBuilder.fromHttpUrl(userInfoUri)
                .queryParam("access_token", accessToken)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return new GoogleResponse(response);
    }

    private String getAccessToken(String code) {
        String url = UriComponentsBuilder.fromHttpUrl(accessTokenUri)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (String) response.get("access_token");
    }
}