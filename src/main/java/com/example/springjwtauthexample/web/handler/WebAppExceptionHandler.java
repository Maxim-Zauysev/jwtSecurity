package com.example.springjwtauthexample.web.handler;

import com.example.springjwtauthexample.exception.AlreadyExistException;
import com.example.springjwtauthexample.exception.EntityNotFoundException;
import com.example.springjwtauthexample.exception.RefreshTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class WebAppExceptionHandler {

    @ExceptionHandler(value = RefreshTokenException.class)
    public ResponseEntity<ErrorResponseBody> refreshTokenExceptionHandler(RefreshTokenException ex, WebRequest webRequest){
        return buildResponse(HttpStatus.FORBIDDEN,ex,webRequest);
    }

    @ExceptionHandler(value = AlreadyExistException.class)
    public ResponseEntity<ErrorResponseBody> alreadyExistHandler(RefreshTokenException ex, WebRequest webRequest){
        return buildResponse(HttpStatus.BAD_REQUEST,ex,webRequest);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> notFoundHandler(RefreshTokenException ex, WebRequest webRequest){
        return buildResponse(HttpStatus.NOT_FOUND,ex,webRequest);
    }


    private ResponseEntity<ErrorResponseBody> buildResponse(HttpStatus httpStatus, RefreshTokenException ex, WebRequest webRequest) {
        return ResponseEntity.status(httpStatus)
                .body(
                        ErrorResponseBody.builder()
                                .message(ex.getMessage())
                                .description(webRequest.getDescription(false))
                                .build()
                );
    }
}
