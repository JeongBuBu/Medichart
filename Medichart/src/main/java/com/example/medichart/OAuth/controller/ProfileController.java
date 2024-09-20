/*
package com.example.medichart.OAuth.controller;

import com.example.medichart.OAuth.dto.CustomOAuth2User;
import com.example.medichart.OAuth.dto.UserDTO;
import com.example.medichart.OAuth.service.SocialUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final SocialUserService socialUserService;

    public ProfileController(SocialUserService socialUserService) {
        this.socialUserService = socialUserService;
    }
    @GetMapping
    public UserDTO getProfile(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomOAuth2User)) {
            throw new RuntimeException("User is not authenticated");
        }

        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();
        String username = user.getUsername();

        // 사용자의 프로필 정보를 가져오는 서비스 호출
        return socialUserService.getUserProfile(username);
    }
}

*/
