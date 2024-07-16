package com.melihinci.skeleton.service;

import com.melihinci.skeleton.entity.User;
import com.melihinci.skeleton.request.LoginRequest;
import org.apache.http.HttpException;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OAuth2Service {

    @Autowired
    UserService userService;

    @Autowired
    TokenCacheService tokenCacheService;

    public String authenticate(LoginRequest loginRequest) throws InvalidCredentialsException {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user != null && userService.checkPassword(user, loginRequest.getPassword())) {
            String token = UUID.randomUUID().toString();
            tokenCacheService.storeToken(token, user);
            return token;
        }
        throw new InvalidCredentialsException("Invalid credentials!");
    }

    public User validateToken(String token) throws InvalidCredentialsException{
        return tokenCacheService.getUserByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid token!"));
    }
}