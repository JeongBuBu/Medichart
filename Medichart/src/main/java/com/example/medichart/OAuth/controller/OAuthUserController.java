/*
package com.example.medichart.OAuth.controller;

import com.example.medichart.OAuth.entity.SocialUserEntity;
import com.example.medichart.OAuth.service.SocialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class OAuthUserController {

    private final SocialUserService socialUserService;

    @Autowired
    public OAuthUserController(SocialUserService socialUserService) {
        this.socialUserService = socialUserService;
    }

    @GetMapping("/info")
    public ResponseEntity<SocialUserEntity> getUserInfo(@RequestParam String username) {
        // username을 이용해 사용자 정보 조회
        SocialUserEntity user = socialUserService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }
}
*/
