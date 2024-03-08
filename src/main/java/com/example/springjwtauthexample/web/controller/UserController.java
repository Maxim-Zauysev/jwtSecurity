package com.example.springjwtauthexample.web.controller;

import com.example.springjwtauthexample.entity.User;
import com.example.springjwtauthexample.mapper.UserMapper;
import com.example.springjwtauthexample.service.BankAccountService;
import com.example.springjwtauthexample.service.UserCommunicationService;
import com.example.springjwtauthexample.web.model.request.*;
import com.example.springjwtauthexample.web.model.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.SupplierReactiveJwtDecoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
@Tag(name = "user v1",description = "user API version v1")
public class UserController {

    private final UserCommunicationService userService;
    private final BankAccountService bankAccountService;
    private final UserMapper userMapper;


    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> userAccess(){
        return ResponseEntity.ok("user response data");
    }


    @Operation(summary = "transfer money", description = "transfer money from user to user. Return ok status", tags = {"user","money"})
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = String.class))
                    }
            )
    )
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> moneyTransaction(@RequestBody @Valid TransferMoneyRequest request,
                                              Authentication authentication){
        bankAccountService.transferMoney(authentication.getName(),request.getRecipient(),request.getAmountMoney());
        return ResponseEntity.ok("Money transferred successfully");
    }

    @PutMapping("/email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateEmail(@RequestBody @Valid ChangeEmailRequest request,
                                                    Authentication authentication){
        String username = authentication.getName();
        User user  = userService.updateEmail(username, request);
        return  ResponseEntity.ok(userMapper.userToResponse(user));
    }

    @PostMapping("/email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> addEmail(@RequestBody @Valid AddOrRemoveEmailRequest request,
                                              Authentication authentication){
        String username = authentication.getName();
        User user  =userService.addEmail(username, request);
        return  ResponseEntity.ok(userMapper.userToResponse(user));
    }

    @DeleteMapping("/email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> deleteEmail(@RequestBody @Valid AddOrRemoveEmailRequest request,
                                           Authentication authentication){
        String username = authentication.getName();
        userService.deleteEmail(username, request);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/phone")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updatePhone(@RequestBody @Valid ChangePhoneRequest request,
                                              Authentication authentication){
        String username = authentication.getName();
        User user  =userService.updatePhone(username, request);
        return  ResponseEntity.ok(userMapper.userToResponse(user));

    }

    @PostMapping("/phone")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> addPhone(@RequestBody @Valid AddOrRemovePhoneRequest request,
                                           Authentication authentication){
        String username = authentication.getName();
        User user  =userService.addPhone(username, request);
        return  ResponseEntity.ok(userMapper.userToResponse(user));

    }

    @DeleteMapping("/phone")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> deletePhone(@RequestBody @Valid AddOrRemovePhoneRequest request,
                                              Authentication authentication){
        String username = authentication.getName();
        userService.deletePhone(username, request);
        return ResponseEntity.noContent().build();

    }


}
