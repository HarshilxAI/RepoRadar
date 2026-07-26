package com.reporadar.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reporadar.config.GithubProperties;
import com.reporadar.exception.ApiException;
import com.reporadar.model.RepositoryReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

@Component
public class GithubClient {
    private static final Logger log = LoggerFactory.getLogger(GithubClient.class);
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final GithubProperties properties;

    public GithubClient(ObjectMapper objectMapper, GithubProperties properties) {
        this.objectMapper = objectMapper;
        this.properties = properties;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(properties.connectTimeoutSeconds()))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public JsonNode repository(RepositoryReference reference) { return getRequired(path(reference, "")); }
    public JsonNode languages(RepositoryReference reference) { return getRequired(path(reference, "/languages")); }
    public Optional<JsonNode> readme(RepositoryReference reference) { return getOptional(path(reference, "/readme")); }
    public JsonNode releases(RepositoryReference reference) { return getRequired(path(reference, "/releases?per_page=100")); }
    public JsonNode contributors(RepositoryReference reference) { return getRequired(path(reference, "/contributors?per_page=100&anon=1")); }
    public JsonNode branches(RepositoryReference reference) { return getRequired(path(reference, "/branches?per_page=100")); }
    public JsonNode pulls(RepositoryReference reference) { return getRequired(path(reference, "/pulls?state=open&per_page=100")); }
    public JsonNode commits(RepositoryReference reference) { return getRequired(path(reference, "/commits?per_page=1")); }
    public Optional<JsonNode> tree(RepositoryReference reference, String branch) {
        return getOptional(path(reference, "/git/trees/" + branch + "?recursive=1"));
    }

    private String path(RepositoryReference reference, String suffix) {
        return "/repos/" + reference.owner() + "/" + reference.name() + suffix;
    }

    private JsonNode getRequired(String path) {
        return getOptional(path).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "This public repository could not be found."));
    }

    private Optional<JsonNode> getOptional(String path) {
        HttpResponse<String> response = request(path);
        if (response.statusCode() == 404) return Optional.empty();
        ensureSuccess(response);
        try {
            return Optional.of(objectMapper.readTree(response.body()));
        } catch (IOException exception) {
            throw new ApiException(HttpStatus.BAD_GATEWAY, "GitHub returned an unexpected response. Please retry.");
        }
    }

    private HttpResponse<String> request(String path) {
        URI uri = URI.create(properties.apiUrl().replaceAll("/$", "") + path);
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri)
                .GET()
                .timeout(Duration.ofSeconds(properties.readTimeoutSeconds()))
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2022-11-28")
                .header("User-Agent", "RepoRadar/1.0");
        if (properties.token() != null && !properties.token().isBlank()) builder.header("Authorization", "Bearer " + properties.token());

        for (int attempt = 1; attempt <= 2; attempt++) {
            try {
                long started = System.nanoTime();
                HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
                log.debug("GitHub {} returned {} in {} ms", path, response.statusCode(), (System.nanoTime() - started) / 1_000_000);
                if (response.statusCode() < 500 || attempt == 2) return response;
            } catch (IOException exception) {
                if (attempt == 2) throw new ApiException(HttpStatus.BAD_GATEWAY, "GitHub is currently unavailable. Please try again shortly.");
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new ApiException(HttpStatus.GATEWAY_TIMEOUT, "The GitHub request timed out. Please try again.");
            }
        }
        throw new ApiException(HttpStatus.BAD_GATEWAY, "GitHub is currently unavailable. Please try again shortly.");
    }

    private void ensureSuccess(HttpResponse<String> response) {
        int status = response.statusCode();
        if (status >= 200 && status < 300) return;
        if (status == 403 && "0".equals(response.headers().firstValue("x-ratelimit-remaining").orElse(""))) {
            throw new ApiException(HttpStatus.TOO_MANY_REQUESTS, "GitHub's rate limit has been reached. Please wait a few minutes and try again.");
        }
        if (status == 401 || status == 403) throw new ApiException(HttpStatus.FORBIDDEN, "This repository is private or cannot be accessed through GitHub's public API.");
        if (status == 422) throw new ApiException(HttpStatus.BAD_REQUEST, "GitHub could not process this repository URL. Please check it and try again.");
        throw new ApiException(HttpStatus.BAD_GATEWAY, "GitHub could not complete the request. Please try again shortly.");
    }
}

