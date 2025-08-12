package com.ML_pipeline.ML_pipeline;

import com.ML_pipeline.ML_pipeline.service.authDB_service_impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ML_pipeline_projectApplication {
	static final Logger logger = LoggerFactory.getLogger(ML_pipeline_projectApplication.class);

	public static void main(String[] args) {

		logger.info("INIT @!$");
		SpringApplication.run(ML_pipeline_projectApplication.class, args);
		logger.info("RUN @!$");
	}
}
