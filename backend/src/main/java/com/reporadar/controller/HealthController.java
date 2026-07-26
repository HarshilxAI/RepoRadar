package com.reporadar.controller;

import com.reporadar.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {
    @Value("${app.version}") private String version;

    @GetMapping("/health")
    public ApiResponse<Map<String, String>> health() {
        return ApiResponse.success("RepoRadar API is healthy.", Map.of("status", "UP", "version", version));
    }
}

