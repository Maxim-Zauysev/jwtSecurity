package com.example.springjwtauthexample.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailRequest{
    private String currentEmail;
    private String newEmail;
}
