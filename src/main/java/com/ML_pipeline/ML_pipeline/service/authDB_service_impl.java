package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.dto.AuthResponse;
import com.ML_pipeline.ML_pipeline.dto.change_password_DTO;
import com.ML_pipeline.ML_pipeline.model.RefreshToken;
import com.ML_pipeline.ML_pipeline.model.User;
import com.ML_pipeline.ML_pipeline.model.UserPrincipal;
import com.ML_pipeline.ML_pipeline.repository.authDB_repo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class authDB_service_impl implements authDB_service{
    @Autowired
    private authDB_repo ar;

    @Autowired
    private JWTService jwts;

    @Autowired
    private RefreshTokenService rts;

    @Autowired
    AuthenticationManager am;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(authDB_service_impl.class);

    @Override
    public void add_user(User user){
        logger.info("ADD_USER @!$");

        user.setPassword(encoder.encode(user.getPassword()));

        ar.save(user);
    }

    @Override
    public AuthResponse verify(User user){
        logger.info("VERIFY @!$");
        Authentication authentication = am.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()){
            String accessToken = jwts.generateToken(user.getUsername());

            // Refresh token
            RefreshToken refreshToken = rts.createRefreshToken(user.getUsername());

            // Return both
            return new AuthResponse(accessToken, refreshToken.getToken());
        }

        return new AuthResponse("Failed", null);
    }

    @Override
    public void edit_user_pw(change_password_DTO pwDTO){
        logger.info("EDIT USER INFO @!$");

        // Get the current user from DB
        User user = ar.findByUsername(pwDTO.getUsername());
        String current_pw = user.getPassword();

        if (encoder.matches(pwDTO.getOld_pass(), current_pw)) {
            logger.info("current EQUALS old");

            // Encode new password and save
            user.setPassword(encoder.encode(pwDTO.getNew_pass()));
            ar.save(user);

            logger.info("Password updated successfully!");
        } else {
            logger.warn("Old password is incorrect!");
        }
    }
}