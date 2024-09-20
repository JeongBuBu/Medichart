
package com.example.medichart.OAuth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private String role;
    private String loginType;  // "social" or "normal"
}

