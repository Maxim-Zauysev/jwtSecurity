package com.example.springjwtauthexample.security;

import com.example.springjwtauthexample.entity.BankAccount;
import com.example.springjwtauthexample.entity.RefreshToken;
import com.example.springjwtauthexample.entity.User;
import com.example.springjwtauthexample.exception.AlreadyExistException;
import com.example.springjwtauthexample.exception.RefreshTokenException;
import com.example.springjwtauthexample.repository.BankAccountRepository;
import com.example.springjwtauthexample.repository.UserRepository;
import com.example.springjwtauthexample.security.jwt.JwtUtils;
import com.example.springjwtauthexample.service.RefreshTokenService;
import com.example.springjwtauthexample.web.model.request.CreateUserRequest;
import com.example.springjwtauthexample.web.model.request.LoginRequest;
import com.example.springjwtauthexample.web.model.request.RefreshTokenRequest;
import com.example.springjwtauthexample.web.model.response.AuthResponse;
import com.example.springjwtauthexample.web.model.response.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BankAccountRepository bankAccountRepository;

    public AuthResponse authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();



        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return AuthResponse.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails))
                .refreshToken(refreshToken.getToken())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();
    }

    public void register(CreateUserRequest request){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        BankAccount bankAccount = new BankAccount();
        bankAccount.setInitialDeposit(BigDecimal.valueOf(100.0));
        bankAccount.setBalance(BigDecimal.valueOf(100.0));

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        try {
            Date date = dateFormat.parse(request.getBirthDate());

            user.setBirthDate(date);
            user.setRoles(request.getRoles());
            user.setEmails(request.getEmails());
            user.setPhones(request.getPhones());
            user.setBankAccount(bankAccount);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(userRepository.existsByUsername(request.getUsername())){
            throw new AlreadyExistException("Username already exist");
        }
        for (String email : request.getEmails()) {
            if (userRepository.existsByEmailsContaining(email)) {
                throw new AlreadyExistException("email already exist");
            }
        }
        for (String email : request.getPhones()) {
            if (userRepository.existsByPhonesContaining(email)) {
                throw new AlreadyExistException("phone already exist");
            }
        }

        bankAccountRepository.save(bankAccount);
        userRepository.save(user);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request){
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByRefreshToken(requestRefreshToken)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getId)
                .map(userId -> {
                    User tokenOwner = userRepository.findById(userId)
                            .orElseThrow(()-> new RefreshTokenException("exception trying to get token for userId:\s" + userId));
                    String token = jwtUtils.generateTokenFromUsername(tokenOwner.getUsername());
                    return new RefreshTokenResponse(token,refreshTokenService.createRefreshToken(userId).getToken());

                }).orElseThrow(()-> new RefreshTokenException(requestRefreshToken,"refresh token not found"));
    }

    public void logout(){
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof AppUserDetails userDetails){
            Long userId = userDetails.getId();
            refreshTokenService.delete(userId);
        }
    }
}
