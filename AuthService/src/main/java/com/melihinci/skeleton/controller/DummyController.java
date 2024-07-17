package com.melihinci.skeleton.controller;

import com.melihinci.skeleton.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {


    @GetMapping("/auth/heartbeat")
    public ResponseEntity<BaseResponse> heartbeat() {
        return ResponseEntity.ok(BaseResponse.builder().message("ImAlive.").status(200).build());
    }
}