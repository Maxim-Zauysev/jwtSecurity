package com.example.springjwtauthexample.web.controller;

import com.example.springjwtauthexample.mapper.UserMapper;
import com.example.springjwtauthexample.service.UserCommunicationService;
import com.example.springjwtauthexample.web.model.request.AddOrRemoveEmailRequest;
import com.example.springjwtauthexample.web.model.request.AddOrRemovePhoneRequest;
import com.example.springjwtauthexample.web.model.request.ChangeEmailRequest;
import com.example.springjwtauthexample.web.model.request.ChangePhoneRequest;
import com.example.springjwtauthexample.web.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class UserController {

    private final UserCommunicationService userService;
    private final UserMapper userMapper;

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess(){
        return "user response data";
    }

    @PutMapping("/email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateEmail(@RequestBody ChangeEmailRequest request,
                                                    Authentication authentication){
        String username = authentication.getName();
        com.example.springjwtauthexample.entity.User user  = userService.updateEmail(username, request);
        return  ResponseEntity.ok(userMapper.userToResponse(user));
    }

    @PostMapping("/email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> addEmail(@RequestBody AddOrRemoveEmailRequest request,
                                              Authentication authentication){
        String username = authentication.getName();
        com.example.springjwtauthexample.entity.User user  =userService.addEmail(username, request);
        return  ResponseEntity.ok(userMapper.userToResponse(user));
    }

    @DeleteMapping("/email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> deleteEmail(@RequestBody AddOrRemoveEmailRequest request,
                                           Authentication authentication){
        String username = authentication.getName();
        userService.deleteEmail(username, request);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/phone")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updatePhone(@RequestBody ChangePhoneRequest request,
                                              Authentication authentication){
        String username = authentication.getName();
        com.example.springjwtauthexample.entity.User user  =userService.updatePhone(username, request);
        return  ResponseEntity.ok(userMapper.userToResponse(user));

    }

    @PostMapping("/phone")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> addPhone(@RequestBody AddOrRemovePhoneRequest request,
                                           Authentication authentication){
        String username = authentication.getName();
        com.example.springjwtauthexample.entity.User user  =userService.addPhone(username, request);
        return  ResponseEntity.ok(userMapper.userToResponse(user));

    }

    @DeleteMapping("/phone")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> deletePhone(@RequestBody AddOrRemovePhoneRequest request,
                                              Authentication authentication){
        String username = authentication.getName();
        userService.deletePhone(username, request);
        return ResponseEntity.noContent().build();

    }


}
