package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.authDB;
import com.ML_pipeline.ML_pipeline.repository.authDB_repo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class authDB_service_impl implements authDB_service{
    @Autowired
    private authDB_repo ar;

    ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(raw_data_service.class);

    @Override
    public void add_user(authDB user){
        logger.info("See user {}", user);

        ar.save(user);
    }
}