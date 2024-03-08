package com.example.springjwtauthexample.web.controller;

import com.example.springjwtauthexample.mapper.UserMapper;
import com.example.springjwtauthexample.service.impl.UserServiceImpl;
import com.example.springjwtauthexample.web.filter.UserFilter;
import com.example.springjwtauthexample.web.model.response.UserListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @GetMapping("/filter")
    public ResponseEntity<UserListResponse> findAllByFilter(UserFilter filter) {
        return ResponseEntity.ok(userMapper.userListToUserListResponse(userService.filterBy(filter)));
    }
}