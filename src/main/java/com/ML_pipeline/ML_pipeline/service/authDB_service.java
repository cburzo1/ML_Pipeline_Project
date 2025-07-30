package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.User;

public interface authDB_service {
    void add_user(User user);
    String verify(User user);
}
