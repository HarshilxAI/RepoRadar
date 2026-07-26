package com.reporadar.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public final class GithubJson {
    private GithubJson() { }

    public static String text(JsonNode node, String field, String fallback) {
        JsonNode value = node.path(field);
        return value.isMissingNode() || value.isNull() ? fallback : value.asText(fallback);
    }

    public static long number(JsonNode node, String field) { return node.path(field).asLong(0); }
    public static Instant instant(JsonNode node, String field) {
        try { return Instant.parse(text(node, field, Instant.EPOCH.toString())); }
        catch (DateTimeParseException exception) { return Instant.EPOCH; }
    }
}

