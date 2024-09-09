package com.example.medichart.OAuth.service;

import com.example.medichart.OAuth.dto.KakaoResponse;
import com.example.medichart.OAuth.entity.UserEntity;
import com.example.medichart.OAuth.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service
public class KakaoOAuth2Service {

    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final String CLIENT_ID = "YOUR_CLIENT_ID";
    private final String REDIRECT_URI = "YOUR_REDIRECT_URI";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public KakaoOAuth2Service(RestTemplate restTemplate, ObjectMapper objectMapper, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    public KakaoResponse getUserInfo(String code) throws IOException {
        // 액세스 토큰 요청
        String tokenResponse = sendPostRequest(KAKAO_TOKEN_URL, Map.of(
                "grant_type", "authorization_code",
                "client_id", CLIENT_ID,
                "redirect_uri", REDIRECT_URI,
                "code", code
        ));

        // JSON 응답에서 액세스 토큰 추출
        JsonNode jsonNode = objectMapper.readTree(tokenResponse);
        String accessToken = jsonNode.get("access_token").asText();

        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token is null or empty");
        }

        // 사용자 정보 요청
        String userInfoResponse = sendGetRequest(KAKAO_USER_INFO_URL, accessToken);
        KakaoResponse kakaoResponse = objectMapper.readValue(userInfoResponse, KakaoResponse.class);

        // 사용자 정보 저장 또는 업데이트
        saveOrUpdateUser(kakaoResponse);

        return kakaoResponse;
    }

    private void saveOrUpdateUser(KakaoResponse kakaoResponse) {
        UserEntity existingUser = userRepository.findByEmail(kakaoResponse.getEmail());

        if (existingUser == null) {
            // 새로운 사용자 생성
            UserEntity newUser = new UserEntity();
            newUser.setUsername(kakaoResponse.getProviderId()); // 카카오 ID를 사용자 이름으로 설정
            newUser.setName(kakaoResponse.getName());
            newUser.setEmail(kakaoResponse.getEmail());
            newUser.setRole("ROLE_USER");

            userRepository.save(newUser);
        } else {
            // 기존 사용자 정보 업데이트
            existingUser.setName(kakaoResponse.getName());
            existingUser.setUsername(kakaoResponse.getProviderId());

            userRepository.save(existingUser);
        }
    }

    private String sendPostRequest(String url, Map<String, String> params) throws IOException {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.setAll(params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new IOException("POST 요청 실패: " + responseEntity.getStatusCode());
        }
    }

    private String sendGetRequest(String url, String accessToken) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new IOException("GET 요청 실패: " + responseEntity.getStatusCode());
        }
    }
}
