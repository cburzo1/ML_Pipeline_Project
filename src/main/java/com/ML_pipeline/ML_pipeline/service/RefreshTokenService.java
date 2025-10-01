package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.RefreshToken;
import com.ML_pipeline.ML_pipeline.model.User;
import org.springframework.stereotype.Service;

public interface RefreshTokenService {
    public RefreshToken createRefreshToken(String username);
    //public void deleteRefreshToken(String token);
}
