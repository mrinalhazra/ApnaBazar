package com.app.apnabazar.service;

import com.app.apnabazar.dto.LoginDto;
import com.app.apnabazar.dto.LoginResponseDto;
import com.app.apnabazar.dto.RegisterDto;
import com.app.apnabazar.exception.AlreadyExists;
import com.app.apnabazar.exception.NotFoundException;
import com.app.apnabazar.model.Cart;
import com.app.apnabazar.model.User;
import com.app.apnabazar.repository.RoleRepository;
import com.app.apnabazar.repository.UserRepository;
import com.app.apnabazar.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private RestTemplate restTemplate;

    private  final String registerUrl = "http://user-service/api/user/register";
    private final String loginUrl = "http://user-service/api/auth/login";

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new NotFoundException("User does not exists.");
        }
        return user.get();
    }


    @Transactional
    public User createUser(UserDto userDto) {
        log.info("Attempting to add a new user: {}", userDto.getUsername());
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if(optionalUser.isPresent()){
            log.info("Username {} already present, throwing error alreadyExists", userDto.getUsername());
            throw new AlreadyExists("Username already exists!, please try a new user");
        }

        //calling the userService to add user.
        //adds the user in the usermaster table
        try {
            RegisterDto registerDto = new RegisterDto
                    (userDto.getName(), userDto.getUsername(), userDto.getEmail(), userDto.getPassword(), userDto.getRoles());
            log.info("calling the userService.AuthService URL {} to create user", registerUrl);
            ResponseEntity<User> response = restTemplate.postForEntity(registerUrl, registerDto, User.class);
            log.info("obtained the response from userservice {}", response);

            if(response.getStatusCode().is2xxSuccessful()){
                log.info("User registration successful. username- {}", response.getBody().getUsername());
                User createdUser = response.getBody();
//                createdUser.setUsername(response.getBody().getUsername());
//                createdUser.setPassword(new BCryptPasswordEncoder().encode(response.getBody().getPassword()));
                createdUser.setName(userDto.getName());
                createdUser.setAddress(userDto.getAddress());
                createdUser.setRoles(userDto.getRoles().stream().map((name) -> roleRepository.findByName(name)).collect(Collectors.toSet()));
                createdUser.setCart(new Cart());
                createdUser.setOrderList(new ArrayList<>());

                userRepository.save(createdUser);
                log.info("User successfully saved in apnabazar repo.");
                return createdUser;
            }else{
                throw new AuthenticationException("Auth service is not working");
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("Failed to add user in repo: "+ e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        if(this.getUserById(id) == null){
            throw new NotFoundException("user not found for this id");
        }
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, UserDto updatedUserDto) {
        if(this.getUserById(id) == null){
            throw new NotFoundException("user not found for this id");
        }

        ResponseEntity<String> response = restTemplate.postForEntity(registerUrl, updatedUserDto, String.class);
         if(response.getStatusCode().is2xxSuccessful()){
             User user = getUserById(id);
             user.setUsername(updatedUserDto.getUsername());
             user.setPassword(updatedUserDto.getPassword());

             userRepository.save(user);
             return user;
         }
        return null;
    }

    public LoginResponseDto login(LoginDto loginDto) {
        log.info("Attempting authorization for- {}", loginDto.getUsername());
        try {
            log.info("Calling the userservice login url {}", loginUrl);
            ResponseEntity<LoginResponseDto> response =  restTemplate.postForEntity(loginUrl, loginDto, LoginResponseDto.class);
            log.info("Obtained response from the userservice - {}", response);
            return response.getBody();
//            LoginResponseDto loginResponseDto = new LoginResponseDto();
//            loginResponseDto.setUsername(response.getBody().getUsername());
//            loginResponseDto.setJwtToken(response.getBody().getJwtToken());
//            return loginResponseDto;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
