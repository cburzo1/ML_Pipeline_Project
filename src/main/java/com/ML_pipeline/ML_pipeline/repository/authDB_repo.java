package com.ML_pipeline.ML_pipeline.repository;

import com.ML_pipeline.ML_pipeline.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface authDB_repo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
