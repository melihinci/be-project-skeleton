package com.melihinci.skeleton.advice;

import com.melihinci.skeleton.entity.User;
import com.melihinci.skeleton.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthFilter implements HandlerInterceptor {

    private final OAuth2Service authService;

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