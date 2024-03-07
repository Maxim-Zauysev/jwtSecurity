package com.example.springjwtauthexample.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOrRemovePhoneRequest {
    @NotEmpty(message = "Phone must not be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone number must consist of 10 digits")
    private String phone;
}
