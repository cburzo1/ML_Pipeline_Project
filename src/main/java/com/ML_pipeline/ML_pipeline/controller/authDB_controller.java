package com.ML_pipeline.ML_pipeline.controller;

import com.ML_pipeline.ML_pipeline.model.authDB;
import com.ML_pipeline.ML_pipeline.model.raw_data;
import com.ML_pipeline.ML_pipeline.service.authDB_service;
import com.ML_pipeline.ML_pipeline.service.raw_data_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class authDB_controller {

    @Autowired
    private authDB_service as;

    @PostMapping("/signup")
    public String signup(@RequestBody authDB user) {
        as.add_user(user);

        return "success add user";
    }
}