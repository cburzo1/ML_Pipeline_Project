package com.ML_pipeline.ML_pipeline.repository;

import com.ML_pipeline.ML_pipeline.model.RefreshToken;
import com.ML_pipeline.ML_pipeline.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
    @Transactional
    void deleteByUser_Id(UUID id);
}
