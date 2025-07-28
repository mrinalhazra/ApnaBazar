package com.ecomApp.userService.controller;

import com.ecomApp.userService.service.AuthService;
import com.ecomApp.userService.dto.LoginRequestDto;
import com.ecomApp.userService.dto.LoginResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/test")
    public String test(){
        return "Successfully started.";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateUserAndLogin(@RequestBody LoginRequestDto loginRequestDto){
        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }




}
