package com.example.medichart.OAuth.dto;

import com.example.medichart.OAuth.dto.OAuth2Response;

import java.util.Map;

public class GoogleResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        // Google 응답에서 "sub" 필드를 사용하여 Provider ID를 추출
        Object id = attribute.get("sub");
        if (id == null) {
            throw new IllegalArgumentException("Provider ID is missing");
        }
        return id.toString();
    }

    @Override
    public String getEmail() {
        // Google 응답에서 "email" 필드를 사용하여 이메일 주소를 추출
        Object email = attribute.get("email");
        if (email == null) {
            throw new IllegalArgumentException("Email is missing");
        }
        return email.toString();
    }

    @Override
    public String getName() {
        // Google 응답에서 "name" 필드를 사용하여 사용자 이름을 추출
        Object name = attribute.get("name");
        if (name == null) {
            throw new IllegalArgumentException("Name is missing");
        }
        return name.toString();
    }
}
