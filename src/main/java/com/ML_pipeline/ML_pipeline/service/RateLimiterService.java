package com.ML_pipeline.ML_pipeline.service;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
public class RateLimiterService {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiterService.class);

    private final Map<String, Queue<Long>> requestLog = new HashMap<>();

    public boolean isAllowed(String username, String ip) {
        logger.info("IS ALLOWED @!$");
        String key = username + ":" + ip;
        long now = System.currentTimeMillis();
        long windowSize = 5 * 60 * 1000; // 5 minutes

        requestLog.putIfAbsent(key, new LinkedList<>());
        Queue<Long> timestamps = requestLog.get(key);

        // Remove timestamps older than window
        while (!timestamps.isEmpty() && timestamps.peek() <= now - windowSize) {
            timestamps.poll();
        }

        // Check limit
        if (timestamps.size() < 5) { // max 5 requests
            timestamps.add(now);
            return true;
        } else {
            return false;
        }
    }
}
