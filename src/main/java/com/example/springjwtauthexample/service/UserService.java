package com.example.springjwtauthexample.service;

import com.example.springjwtauthexample.entity.User;
import com.example.springjwtauthexample.web.filter.UserFilter;

import java.util.Date;
import java.util.List;

public interface UserService {
    List<User> filterBy(UserFilter filter);
    User findByUsername(String username);
}
