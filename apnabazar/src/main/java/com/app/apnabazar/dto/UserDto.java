package com.app.apnabazar.dto;

import com.app.apnabazar.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String username;
    private String email;
    private String password;
    private String address;


    private Set<String> roles;
}
