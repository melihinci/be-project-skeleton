package com.melihinci.skeleton.repository;

import com.melihinci.skeleton.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}