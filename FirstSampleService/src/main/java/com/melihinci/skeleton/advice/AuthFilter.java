package com.melihinci.skeleton.advice;

import com.melihinci.skeleton.entity.User;
import com.melihinci.skeleton.service.OAuth2Service;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthFilter implements HandlerInterceptor {

    @Autowired
    private OAuth2Service authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authToken = request.getHeader("X-Auth-Token");
        if (authToken == null || authToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        User user = authService.validateToken(authToken);
        if (user == null) {
            throw new InvalidCredentialsException("Invalid token!");
        }
        return true;
    }
}