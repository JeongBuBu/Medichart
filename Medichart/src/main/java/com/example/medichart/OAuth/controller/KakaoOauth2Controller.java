package com.example.medichart.OAuth.controller;

import com.example.medichart.OAuth.dto.KakaoResponse;
import com.example.medichart.OAuth.service.KakaoOAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2/code/kakao")
public class KakaoOauth2Controller {

    @Autowired
    private KakaoOAuth2Service kakaoOAuth2Service;

    @GetMapping
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {
        try {
            // 인증 코드로 카카오 사용자 정보 가져오기
            KakaoResponse userResponse = kakaoOAuth2Service.getUserInfo(code);

            // 사용자 정보를 처리하고, 로그인 처리 등의 로직을 추가합니다.
            // 예를 들어, 사용자 정보를 데이터베이스에 저장하거나, 세션을 설정할 수 있습니다.
            System.out.println("code" + code);
            return ResponseEntity.ok(userResponse); // 필요한 정보를 클라이언트에 응답
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 처리 중 오류 발생");
        }
    }
}
