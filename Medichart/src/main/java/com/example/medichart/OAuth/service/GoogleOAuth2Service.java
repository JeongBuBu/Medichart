package com.example.medichart.OAuth.service;

import com.example.medichart.OAuth.dto.GoogleResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GoogleOAuth2Service {

    private final String userInfoUri = "https://www.googleapis.com/oauth2/v3/userinfo";

    public GoogleResponse getUserInfo(String code) {
        // RestTemplate을 사용하여 사용자 정보를 가져옴
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = getAccessToken(code); // 이 메서드를 통해 액세스 토큰을 가져오는 로직 필요

        String url = userInfoUri + "?access_token=" + accessToken;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        return new GoogleResponse(response);
    }

    private String getAccessToken(String code) {
        // 액세스 토큰을 가져오는 로직 필요
        return "access_token"; // 액세스 토큰을 가져오는 실제 로직을 구현해야 함
    }
}
