package com.abhishek.SamsTrack.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String role;
    private String token;
    private String password;
}


