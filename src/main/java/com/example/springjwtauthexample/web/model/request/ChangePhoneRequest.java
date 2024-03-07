package com.example.springjwtauthexample.web.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePhoneRequest {
    @NotEmpty(message = "Current phone must not be empty")
    @Pattern(regexp = "\\d{10}", message = "Current phone number must consist of 10 digits")
    private String currentPhone;

    @NotEmpty(message = "New phone must not be empty")
    @Pattern(regexp = "\\d{10}", message = "New phone number must consist of 10 digits")
    private String newPhone;
}
