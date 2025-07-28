package com.ecomApp.userService.controller;

import com.ecomApp.userService.model.User;
import com.ecomApp.userService.service.UserService;
import com.ecomApp.userService.dto.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody SignUpDto signUpDto){
       User createdUser =  userService.registerUser(signUpDto);
       return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
