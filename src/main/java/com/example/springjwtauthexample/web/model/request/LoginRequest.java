package com.example.springjwtauthexample.web.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "Username must not be empty")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    private String password;
}
