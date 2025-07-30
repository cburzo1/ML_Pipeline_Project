package com.ML_pipeline.ML_pipeline.Configuration;

import com.ML_pipeline.ML_pipeline.ML_pipeline_projectApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppRestTemplateConfig {
    static final Logger logger = LoggerFactory.getLogger(AppRestTemplateConfig.class);

    @Bean
    public RestTemplate restTemplate() {
        logger.info("REST TEMPLATE @!$");
        return new RestTemplate();
    }

}