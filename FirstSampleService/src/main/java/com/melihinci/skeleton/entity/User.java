package com.melihinci.skeleton.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
@Builder
public class User implements Serializable{

    private Long id;
    private String authorities;
    private String username;
    private String password;

}