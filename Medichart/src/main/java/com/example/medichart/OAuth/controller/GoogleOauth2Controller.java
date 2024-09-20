package com.example.medichart.OAuth.controller;

import com.example.medichart.OAuth.dto.GoogleResponse;
import com.example.medichart.OAuth.service.GoogleOAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2/code/google")
public class GoogleOauth2Controller {

    @Autowired
    private GoogleOAuth2Service googleOAuth2Service;

    @GetMapping
    public ResponseEntity<?> googleCallback(@RequestParam("code") String code) {
        try {
            // 인증 코드로 Google 사용자 정보 가져오기
            GoogleResponse userResponse = googleOAuth2Service.getUserInfo(code);

            // 사용자 정보를 처리하고, 로그인 처리 등의 로직을 추가합니다.
            System.out.println("code: " + code);
            return ResponseEntity.ok(userResponse); // 필요한 정보를 클라이언트에 응답
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("로그인 처리 중 오류 발생");
        }
    }
}
