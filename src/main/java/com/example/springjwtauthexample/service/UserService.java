package com.example.springjwtauthexample.service;

import com.example.springjwtauthexample.entity.User;
import com.example.springjwtauthexample.web.filter.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface UserService {
    List<User> filterBy(UserFilter filter);

}
