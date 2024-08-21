package com.melihinci.skeleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.melihinci.skeleton","com.melihinci.skeleton.config"})
public class FirstSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstSampleApplication.class, args);
    }
}
