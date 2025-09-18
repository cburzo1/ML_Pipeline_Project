package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.RefreshToken;
import com.ML_pipeline.ML_pipeline.model.User;
import com.ML_pipeline.ML_pipeline.repository.RefreshTokenRepo;
import com.ML_pipeline.ML_pipeline.repository.authDB_repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenRepo rtr;

    @Autowired
    private authDB_repo ar;

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    public RefreshToken createRefreshToken(String username){
        logger.info("CREATE REFRESH TOKEN UUID @!$");
        User user = ar.findByUsername(username);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString()); // random string for token
        refreshToken.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS)); // example: 30-day expiry

        return rtr.save(refreshToken);
    }
}
