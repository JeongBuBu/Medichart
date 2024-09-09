package com.example.medichart.OAuth.dto;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;  // 직접 "kakao_account"를 사용하지 않고 attribute 전체를 사용합니다.
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        // "id"는 최상위 레벨에 있으므로 직접 접근
        Object id = attribute.get("id");
        if (id == null) {
            throw new IllegalArgumentException("Provider ID is missing");
        }
        return id.toString();
    }

    @Override
    public String getEmail() {
        // "email"은 "kakao_account" 내부에 위치
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        if (kakaoAccount == null || kakaoAccount.get("email") == null) {
            throw new IllegalArgumentException("Email is missing");
        }
        return kakaoAccount.get("email").toString();
    }

    @Override
    public String getName() {
        // "name"은 "kakao_account" 내부에 위치
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        if (kakaoAccount == null || kakaoAccount.get("name") == null) {
            throw new IllegalArgumentException("Name is missing");
        }
        return kakaoAccount.get("name").toString();
    }
}
