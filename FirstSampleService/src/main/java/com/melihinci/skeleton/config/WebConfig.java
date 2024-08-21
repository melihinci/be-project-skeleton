package com.melihinci.skeleton.config;

import com.melihinci.skeleton.advice.AuthFilter;
import com.melihinci.skeleton.advice.LoggingInterceptor;
import com.melihinci.skeleton.service.OAuth2Service;
import com.melihinci.skeleton.service.TokenCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    private final Environment env;

    public WebConfig(Environment env) {
        this.env = env;
    }

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private final String[] excludePathPatterns = new String[]{"/heartbeat", "swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthFilter(new OAuth2Service(new TokenCacheService(redisTemplate))))
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
        registry.addInterceptor(new LoggingInterceptor(env));
    }
}