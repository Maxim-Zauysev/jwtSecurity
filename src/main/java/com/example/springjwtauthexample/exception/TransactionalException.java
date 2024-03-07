package com.example.springjwtauthexample.exception;

public class TransactionalException extends RuntimeException{
    public TransactionalException(String message) {
        super(message);
    }
}
