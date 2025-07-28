package com.app.apnabazar.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String username;
    private String jwtToken;
}
