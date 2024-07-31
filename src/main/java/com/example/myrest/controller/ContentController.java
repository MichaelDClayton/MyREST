package com.example.myrest.controller;

import com.example.myrest.service.JWTService;
import com.example.myrest.model.LoginForm;
import com.example.myrest.service.MyUserDetailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContentController {



    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MyUserDetailService myUserDetailService;

    public ContentController(AuthenticationManager authenticationManager, JWTService jwtService, MyUserDetailService myUserDetailService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.myUserDetailService = myUserDetailService;
    }

    @GetMapping("/home")
    public String handleWelcome() {
        return "Welcome to home!";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome() {
        return "Welcome to ADMIN home!";
    }

    @GetMapping("/user/home")
    public String handleUserHome() {
        return "Welcome to USER home!";
    }

    @GetMapping("/admin/home/users/all")
    public List<UserDetails> getAllUsers(){
        return myUserDetailService.loadAllUsers();
    }

    @GetMapping("/admin/home/users/{id}")
    public UserDetails findUserById(@PathVariable Long id) {
      return myUserDetailService.findUserById(id);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

}
