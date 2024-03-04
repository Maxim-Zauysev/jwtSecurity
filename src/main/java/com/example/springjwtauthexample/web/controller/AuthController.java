package com.example.springjwtauthexample.web.controller;

import com.example.springjwtauthexample.exception.AlreadyExistException;
import com.example.springjwtauthexample.repository.UserRepository;
import com.example.springjwtauthexample.security.SecurityService;
import com.example.springjwtauthexample.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(securityService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> registerUser(@RequestBody CreateUserRequest userRequest){
        if(userRepository.existsByUsername(userRequest.getUsername())){
            throw new AlreadyExistException("Username already exist");
        }
        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new AlreadyExistException("email already exist");
        }
        securityService.register(userRequest);
        return ResponseEntity.ok(new SimpleResponse("User created"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(securityService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        securityService.logout();
        return ResponseEntity.ok(new SimpleResponse("User logout. Username is:\s" + userDetails.getUsername()));
    }

}