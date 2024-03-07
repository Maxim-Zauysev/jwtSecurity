package com.example.springjwtauthexample.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailRequest {
    @NotEmpty(message = "Current email must not be empty")
    @Email(message = "Current email should be valid")
    private String currentEmail;

    @NotEmpty(message = "New email must not be empty")
    @Email(message = "New email should be valid")
    private String newEmail;
}
