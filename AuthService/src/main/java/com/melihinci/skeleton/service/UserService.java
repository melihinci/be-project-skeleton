package com.melihinci.skeleton.service;

import com.melihinci.skeleton.entity.User;
import com.melihinci.skeleton.repository.UserRepository;
import com.melihinci.skeleton.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String ROLE_USER = "ROLE_USER";

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public User createUser(AuthRequest authRequest) {
        return userRepository.save(User.builder()
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .username(authRequest.getUsername())
                .authorities(ROLE_USER)
                .build());
    }
}