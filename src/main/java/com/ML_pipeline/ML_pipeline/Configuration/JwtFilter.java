package com.ML_pipeline.ML_pipeline.Configuration;

import com.ML_pipeline.ML_pipeline.service.JWTService;
import com.ML_pipeline.ML_pipeline.service.MyUserDetailsService;
import com.ML_pipeline.ML_pipeline.service.RateLimiterService;
import com.ML_pipeline.ML_pipeline.service.authDB_service;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JWTService jwts;

    @Autowired
    ApplicationContext context;

    @Autowired
    RateLimiterService rateLimiterService;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("DO FILTER INTERNAL @!$");

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String ip = request.getRemoteAddr();

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            //Skip Bearer string
            token = authHeader.substring(7);
            username = jwts.extractUserName(token);

            try {
                if (jwts.isTokenExpired(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                    response.getWriter().write("Access token expired");
                    return; // stop the filter chain
                }

                // Optional: set authentication in SecurityContext
                // SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid access token");
                return;
            }
        } else {
            // No token → reject or continue depending on your policy
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing access token");
            return;
        }

        if (!rateLimiterService.isAllowed(username, ip)) {
            response.setStatus(429);
            return;
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user_details = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            //Create authentication obj
            if(jwts.validateToken(token, user_details)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user_details, null, user_details.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        if (jwts.isBlackListed(token)) {
            logger.info("Blocked request with blacklisted token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
