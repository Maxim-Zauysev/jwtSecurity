package com.example.springjwtauthexample.security;

import com.example.springjwtauthexample.entity.RefreshToken;
import com.example.springjwtauthexample.entity.User;
import com.example.springjwtauthexample.exception.RefreshTokenException;
import com.example.springjwtauthexample.repository.UserRepository;
import com.example.springjwtauthexample.security.jwt.JwtUtils;
import com.example.springjwtauthexample.service.RefreshTokenService;
import com.example.springjwtauthexample.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    public void register(CreateUserRequest request){
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        user.setRoles(request.getRoles());
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
