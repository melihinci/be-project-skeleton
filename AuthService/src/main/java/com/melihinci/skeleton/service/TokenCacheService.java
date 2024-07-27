package com.melihinci.skeleton.service;

import com.melihinci.skeleton.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TokenCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String TOKEN_PREFIX = "xAuthToken ";

    public void storeToken(String token, User user) {
        redisTemplate.opsForValue()
                     .set(TOKEN_PREFIX + token, user, 1, TimeUnit.HOURS); // Token'ı 1 saat geçerli yap
    }

    public Optional<User> getUserByToken(String token) {
        return Optional.of((User) redisTemplate.opsForValue()
                                               .get(TOKEN_PREFIX + token));
    }
}