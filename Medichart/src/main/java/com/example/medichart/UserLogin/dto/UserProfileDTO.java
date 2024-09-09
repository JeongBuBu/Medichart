package com.example.medichart.UserLogin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfileDTO {

    private String email;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
}