package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface authDB_service {
    void add_user(User user);
    String verify(User user);
    void edit_user_info(UserDTO udto, User user, String info);
}
