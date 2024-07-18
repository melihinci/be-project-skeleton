package com.melihinci.skeleton.service;

import com.melihinci.skeleton.entity.User;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OAuth2Service {

    @Autowired
    TokenCacheService tokenCacheService;

    public User validateToken(String token) throws InvalidCredentialsException {
        return tokenCacheService.getUserByToken(token)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid token!"));
    }
}