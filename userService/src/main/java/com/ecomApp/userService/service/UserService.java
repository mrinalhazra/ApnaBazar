package com.ecomApp.userService.service;

import com.ecomApp.userService.exception.NotFoundException;
import com.ecomApp.userService.exception.UserAlreadyExists;
import com.ecomApp.userService.model.Roles;
import com.ecomApp.userService.model.User;
import com.ecomApp.userService.repository.RoleRepository;
import com.ecomApp.userService.repository.UserRepository;
import com.ecomApp.userService.dto.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User registerUser(SignUpDto signUpDto) {
      Optional<User> existingUser = userRepository.findByUsername(signUpDto.getUsername());
      if(existingUser.isPresent()){
          throw new UserAlreadyExists("This user already exists.");
      }
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpDto.getPassword()));

        Set<Roles> roles = new HashSet<>();
        if(signUpDto.getRoles() == null || signUpDto.getRoles().isEmpty()){
            Roles defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new NotFoundException("Default role not found"));
            roles.add(defaultRole);

        }else {
            for(String role : signUpDto.getRoles()){
                Roles setRole = roleRepository.findByName(role).orElseThrow(() -> new NotFoundException(role + " Does not exists."));
                roles.add(setRole);
            }
        }
        user.setRoleSet(roles);
        userRepository.save(user);

        return user;
    }

    public String getStoredHashedPassword(String userName) {
       return userRepository.findByUsername(userName).get().getPassword();
    }
}
