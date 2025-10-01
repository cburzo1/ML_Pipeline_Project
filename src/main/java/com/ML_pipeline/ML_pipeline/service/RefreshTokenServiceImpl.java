package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.RefreshToken;
import com.ML_pipeline.ML_pipeline.model.User;
import com.ML_pipeline.ML_pipeline.repository.RefreshTokenRepo;
import com.ML_pipeline.ML_pipeline.repository.authDB_repo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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

    @Autowired
    private JWTService jwts;

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    @Transactional
    public RefreshToken createRefreshToken(String username){
        logger.info("CREATE REFRESH TOKEN UUID @!$");
        User user = ar.findByUsername(username);
        RefreshToken refreshToken = rtr.findByUser_Id(user.getId())
                .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));

        return rtr.save(refreshToken);
    }

    /*@Transactional
    public void deleteRefreshToken(String token){

        String username = jwts.extractUserName(token);
        logger.info("FOUND REFRESH TOKEN OF USER {}", username);

        User user2 = ar.findByUsername(username);

        logger.info("ID HERE :: {}",user2.getId());

        rtr.deleteByUser_Id(user2.getId());
    }*/
}
