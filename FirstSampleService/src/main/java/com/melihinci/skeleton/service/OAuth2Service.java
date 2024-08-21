package com.melihinci.skeleton.service;

import com.melihinci.skeleton.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final TokenCacheService tokenCacheService;

    public User validateToken(String token) throws InvalidCredentialsException {
        return tokenCacheService.getUserByToken(token)
                                .orElseThrow(() -> new InvalidCredentialsException("Invalid token!"));
    }
}