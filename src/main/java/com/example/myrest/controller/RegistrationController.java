package com.example.myrest.controller;

import com.example.myrest.model.MyUser;
import com.example.myrest.repository.MyUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final MyUserRepository myUserRepository;

    public RegistrationController(PasswordEncoder passwordEncoder, MyUserRepository myUserRepository){
        this.passwordEncoder = passwordEncoder;
        this.myUserRepository = myUserRepository;
    }

    @PostMapping("/register/user")
    public MyUser registerUser(@RequestBody MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return myUserRepository.save(myUser);
    }
}
