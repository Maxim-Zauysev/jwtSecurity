package com.example.springjwtauthexample.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePhoneRequest {
    private String currentPhone;
    private String newPhone;
}
