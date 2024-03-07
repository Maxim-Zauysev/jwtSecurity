package com.example.springjwtauthexample.web.model.request;

import com.example.springjwtauthexample.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    @NotNull(message = "Username must not be null")
    @Size(min = 1, message = "Username must not be empty")
    private String username;

    @NotEmpty(message = "Emails must not be empty")
    private Set<String> emails;

    @NotEmpty(message = "Roles must not be empty")
    private Set<RoleType> roles;

    @NotNull(message = "Password must not be null")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birth date must be in the format yyyy-MM-dd")
    private String birthDate;

    @NotEmpty(message = "Phones must not be empty")
    private Set<String> phones;
}
