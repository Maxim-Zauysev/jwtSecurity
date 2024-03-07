package com.example.springjwtauthexample.web.model.request;

import com.example.springjwtauthexample.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    private String username;
    private Set<String> emails;
    private Set<RoleType> roles;
    private String password;
    private String birthDate;
    private Set<String> phones;

}
