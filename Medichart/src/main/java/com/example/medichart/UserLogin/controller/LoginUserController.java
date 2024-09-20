package com.example.medichart.UserLogin.controller;

import com.example.medichart.OAuth.dto.CustomOAuth2User;
import com.example.medichart.OAuth.dto.UserDTO;
import com.example.medichart.OAuth.entity.SocialUserEntity;
import com.example.medichart.OAuth.exception.UserNotFoundException;
import com.example.medichart.OAuth.service.SocialUserService;
import com.example.medichart.UserLogin.dto.CustomUserDetails;
import com.example.medichart.UserLogin.dto.UserProfileDTO;
import com.example.medichart.UserLogin.entity.NormalUserEntity;
import com.example.medichart.UserLogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginUserController {

    private final UserService userService;
    private final SocialUserService socialUserService;

    @Autowired
    public LoginUserController(UserService userService, SocialUserService socialUserService) {
        this.userService = userService;
        this.socialUserService = socialUserService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        try {
            if (authentication.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails normalUserDetails = (CustomUserDetails) authentication.getPrincipal();
                UserProfileDTO profile = userService.getUserProfile(normalUserDetails.getUsername());
                return ResponseEntity.ok(profile);
            } else if (authentication.getPrincipal() instanceof CustomOAuth2User) {
                CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                String username = oauthUser.getUsername();
                UserDTO profile = socialUserService.getUserProfile(username);
                return ResponseEntity.ok(profile);
            } else {
                throw new RuntimeException("Unknown authentication type");
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String identifier) {
        try {
            NormalUserEntity normalUser = userService.findByEmail(identifier);
            if (normalUser != null) {
                return ResponseEntity.ok(normalUser);
            }

            SocialUserEntity socialUser = socialUserService.findByUsername(identifier);
            if (socialUser != null) {
                return ResponseEntity.ok(socialUser);
            }

            throw new UserNotFoundException("User with identifier " + identifier + " not found");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
}