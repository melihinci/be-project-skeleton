package com.melihinci.skeleton.controller;

import com.melihinci.skeleton.entity.User;
import com.melihinci.skeleton.request.LoginRequest;
import com.melihinci.skeleton.response.AccessTokenResponse;
import com.melihinci.skeleton.service.OAuth2Service;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private OAuth2Service oAuth2Service;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) throws InvalidCredentialsException {
        String accessToken = oAuth2Service.authenticate(loginRequest);
        return ResponseEntity.ok(new AccessTokenResponse(accessToken));
    }


    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("X-Auth-Token") String token) throws InvalidCredentialsException {
        User user = oAuth2Service.validateToken(token);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).body("Invalid token");
    }
}