package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.dto.AuthResponse;
import com.ML_pipeline.ML_pipeline.dto.change_password_DTO;
import com.ML_pipeline.ML_pipeline.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface authDB_service {
    void add_user(User user);
    AuthResponse verify(User user);
    void edit_user_pw(change_password_DTO pwDTOr);
}
