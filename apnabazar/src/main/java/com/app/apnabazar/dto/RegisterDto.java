package com.app.apnabazar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String name;

    private String username;
    private String email;
    private String password;

    private Set<String> roles;
}
