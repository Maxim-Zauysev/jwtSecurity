package com.example.springjwtauthexample.web.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFilter {
   private String username;
   private String birthDate;
   private String email;
   private String phone;
   private Integer pageNumber;
   private Integer pageSize;
}
