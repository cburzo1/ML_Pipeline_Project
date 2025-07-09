package com.ML_pipeline.ML_pipeline.controller;

import com.ML_pipeline.ML_pipeline.model.User;
import com.ML_pipeline.ML_pipeline.service.authDB_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class authDB_controller {

    @Autowired
    private authDB_service as;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        as.add_user(user);

        return "success add user";
    }

    @GetMapping("/")
    public String hello() {
        return "Hello world!";
    }
}