package com.melihinci.skeleton.service;

import com.melihinci.skeleton.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenCacheService {


    private final RedisTemplate<String, Object> redisTemplate;

    private static final String TOKEN_PREFIX = "xAuthToken ";

    public void storeToken(String token, User user) {
        redisTemplate.opsForValue()
                     .set(TOKEN_PREFIX + token, user, 1, TimeUnit.HOURS); // Token'ı 1 saat geçerli yap
    }

    public Optional<User> getUserByToken(String token) {
        LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap) redisTemplate.opsForValue()
                                                                                   .get(TOKEN_PREFIX + token);
        return linkedHashMap == null ? Optional.empty() : Optional.of(User.builder()
                                                                          .id(Long.valueOf((String) linkedHashMap.get("id")))
                                                                          .authorities((String) linkedHashMap.get("authorities"))
                                                                          .username((String) linkedHashMap.get("username"))
                                                                          .password((String) linkedHashMap.get("password"))
                                                                          .build());
    }
}