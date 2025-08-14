package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.RefreshToken;
import com.ML_pipeline.ML_pipeline.repository.RefreshTokenRepo;
import com.ML_pipeline.ML_pipeline.repository.authDB_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenRepo rtr;

    @Autowired
    private authDB_repo ar;

    public RefreshToken createRefreshToken(String username){
        ar.findByUsername(username);
    }
}
