package com.example.springjwtauthexample.exception;

public class WrongDataFormatException extends RuntimeException{
    public WrongDataFormatException(String message) {
        super(message);
    }
}