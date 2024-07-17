package com.melihinci.skeleton.controller;

import com.melihinci.skeleton.entity.User;
import com.melihinci.skeleton.request.AuthRequest;
import com.melihinci.skeleton.response.AccessTokenResponse;
import com.melihinci.skeleton.response.BaseResponse;
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

    @PostMapping("/auth/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody AuthRequest authRequest) throws InvalidCredentialsException {
        String accessToken = oAuth2Service.authenticate(authRequest);
        return ResponseEntity.ok(new AccessTokenResponse(accessToken));
    }


    @GetMapping("/auth/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("X-Auth-Token") String token) throws InvalidCredentialsException {
        User user = oAuth2Service.validateToken(token);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<BaseResponse> signup(@RequestBody AuthRequest authRequest) {
        oAuth2Service.signup(authRequest);
        return ResponseEntity.ok(   new BaseResponse("User created successfully",200));
    }
}