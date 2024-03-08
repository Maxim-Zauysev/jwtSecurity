package com.example.springjwtauthexample.web.controller;


import com.example.springjwtauthexample.exception.*;
import com.example.springjwtauthexample.web.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionHandierController {



    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponse> alreadyExist(AlreadyExistException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(RemoveLastElementException.class)
    public ResponseEntity<ErrorResponse> lastElementCollectionException(RemoveLastElementException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ErrorResponse> refreshException(RefreshTokenException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(WrongDataFormatException.class)
    public ResponseEntity<ErrorResponse> refreshException(WrongDataFormatException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(TransactionalException.class)
    public ResponseEntity<ErrorResponse> refreshException(TransactionalException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> notValid(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

        String errorMessage = String.join(";",errorMessages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }
}

