package com.melihinci.skeleton.advice;

import com.melihinci.skeleton.service.OAuth2Service;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private OAuth2Service authService;
    private final String[] EXCLUDE_URLS = new String[]{"/auth/heartbeat", "/auth/login", "/auth/signup", "/auth/validate", "swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"};

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authToken = request.getHeader("X-Auth-Token");
        if (authToken == null || authToken.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter()
                    .write("Unauthorized: Invalid or missing authentication token");
            return;
        }

        try {
            authService.validateToken(authToken);
        } catch (InvalidCredentialsException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter()
                    .write("Unauthorized: Invalid or missing authentication token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        for (String excludeUrl : EXCLUDE_URLS) {
            if (path.startsWith(excludeUrl)) {
                return true;
            }
        }
        return false;
    }
}