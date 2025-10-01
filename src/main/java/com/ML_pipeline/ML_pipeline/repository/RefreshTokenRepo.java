package com.ML_pipeline.ML_pipeline.repository;

import com.ML_pipeline.ML_pipeline.model.RefreshToken;
import com.ML_pipeline.ML_pipeline.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {
    @Transactional
    @Modifying
    void deleteByUser_Id(UUID id);

    Optional<RefreshToken> findByUser_Id(UUID id);

    Optional<RefreshToken> findByToken(String refreshTokenStr);
}
