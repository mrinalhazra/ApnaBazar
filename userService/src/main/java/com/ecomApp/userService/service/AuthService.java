package com.ecomApp.userService.service;

import com.ecomApp.userService.jwt.JwtAuthenticationHelper;
import com.ecomApp.userService.dto.LoginRequestDto;
import com.ecomApp.userService.dto.LoginResponseDto;
import com.ecomApp.userService.security.CustomUserdetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    CustomUserdetailsService customUserdetailsService;

    @Autowired
    UserService userService;

    @Autowired
    JwtAuthenticationHelper jwtAuthenticationHelper;



    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        this.doAuthenticate(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        log.info("Authentication of the user credentials is successful.");

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        log.info("User details fetched from username :- {}", userDetails);

        String token = jwtAuthenticationHelper.generateToken(userDetails);
        log.info("token created - {}", token);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setJwtToken(token);
//        loginResponseDto.setEmail(loginRequestDto.getUserNameOrEmail());
        loginResponseDto.setUsername(loginRequestDto.getUsername());

        return loginResponseDto;
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials");
        }
//        String storedPassword = userService.getStoredHashedPassword(userName);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//       if(passwordEncoder.matches(password, storedPassword)){
//
//       }else throw new BadCredentialsException("Invalid credentials");



    }
}
