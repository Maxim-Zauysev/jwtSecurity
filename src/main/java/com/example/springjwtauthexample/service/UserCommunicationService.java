package com.example.springjwtauthexample.service;

import com.example.springjwtauthexample.entity.User;

import com.example.springjwtauthexample.web.model.request.AddOrRemoveEmailRequest;
import com.example.springjwtauthexample.web.model.request.AddOrRemovePhoneRequest;
import com.example.springjwtauthexample.web.model.request.ChangeEmailRequest;
import com.example.springjwtauthexample.web.model.request.ChangePhoneRequest;

public interface UserCommunicationService  {

    User updateEmail(String username, ChangeEmailRequest request);
    User addEmail(String username, AddOrRemoveEmailRequest request);
    User updatePhone(String username, ChangePhoneRequest request);
    User addPhone(String username, AddOrRemovePhoneRequest request);
    void deleteEmail(String username, AddOrRemoveEmailRequest request);
    void deletePhone(String username, AddOrRemovePhoneRequest request);
}
