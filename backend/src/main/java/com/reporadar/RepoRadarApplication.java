package com.reporadar;

import com.reporadar.config.GithubProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GithubProperties.class)
public class RepoRadarApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepoRadarApplication.class, args);
    }
}

