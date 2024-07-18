package com.melihinci.skeleton.config;

import com.melihinci.skeleton.advice.AuthFilter;
import com.melihinci.skeleton.advice.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Environment env;

    public WebConfig(Environment env) {
        this.env = env;
    }

    private final String[] excludePathPatterns = new String[]{"/heartbeat"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthFilter())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
        registry.addInterceptor(new LoggingInterceptor(env));
    }
}