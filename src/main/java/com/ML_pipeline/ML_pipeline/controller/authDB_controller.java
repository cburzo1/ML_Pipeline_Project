package com.ML_pipeline.ML_pipeline.controller;

import com.ML_pipeline.ML_pipeline.ML_pipeline_projectApplication;
import com.ML_pipeline.ML_pipeline.dto.AuthResponse;
import com.ML_pipeline.ML_pipeline.dto.change_password_DTO;
import com.ML_pipeline.ML_pipeline.model.User;
import com.ML_pipeline.ML_pipeline.service.JWTService;
import com.ML_pipeline.ML_pipeline.service.RefreshTokenService;
import com.ML_pipeline.ML_pipeline.service.authDB_service;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class authDB_controller {
    static final Logger logger = LoggerFactory.getLogger(authDB_controller.class);

    @Autowired
    private authDB_service as;

    @Autowired
    private JWTService jwts;

    @Autowired
    private RefreshTokenService rts;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        logger.info("SIGNUP @!$");
        as.add_user(user);

        return "success add user";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody User user){
        logger.info("LOGIN @!$");

        return as.verify(user);
    }

    @PostMapping("/change-password")
    public String change_password(@RequestBody change_password_DTO pwDTO){
        logger.info("CHANGE PASSWORD @!$");

        as.edit_user_pw(pwDTO);

        return "Password change successful";
    }

    @PostMapping("/logout")
    public String logout(@RequestBody User user, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // remove "Bearer "

            rts.deleteRefreshToken(user);

            jwts.blacklist(token); // We'll implement this later
            return "Successfully Logged Out";
        } else {
            return "No token provided";
        }
    }

    @GetMapping("/")
    public String hello() {
        logger.info("HELLO @!$");
        return "Hello world!";
    }
}