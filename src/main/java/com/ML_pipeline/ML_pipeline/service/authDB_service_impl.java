package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.model.User;
import com.ML_pipeline.ML_pipeline.model.UserPrincipal;
import com.ML_pipeline.ML_pipeline.repository.authDB_repo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class authDB_service_impl implements authDB_service{
    @Autowired
    private authDB_repo ar;

    @Autowired
    private JWTService jwts;

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
    public String verify(User user){
        logger.info("VERIFY @!$");
        Authentication authentication = am.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwts.generateToken(user.getUsername());
        }

        return "Failed";
    }

    @Override
    public void edit_user_info(HttpServletRequest request, User user, String info){
        logger.info("EDIT USER INFO @!$");
        if(Objects.equals(info, "pw")){
            logger.info(String.valueOf(request), user, info);
        }
    }
}