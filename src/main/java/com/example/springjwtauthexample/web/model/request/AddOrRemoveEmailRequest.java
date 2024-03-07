package com.example.springjwtauthexample.web.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOrRemoveEmailRequest {
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    private String email;
}
