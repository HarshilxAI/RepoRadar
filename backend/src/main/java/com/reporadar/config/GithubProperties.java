package com.reporadar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github")
public record GithubProperties(
        String apiUrl,
        String token,
        int connectTimeoutSeconds,
        int readTimeoutSeconds
) { }

