package com.reporadar.utils;

import com.reporadar.exception.ApiException;
import com.reporadar.model.RepositoryReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RepositoryUrlParser {
    private static final Pattern PATH = Pattern.compile("^/([A-Za-z0-9_.-]+)/([A-Za-z0-9_.-]+?)(?:\\.git)?/?$");

    public RepositoryReference parse(String repositoryUrl) {
        if (repositoryUrl == null || repositoryUrl.isBlank()) throw invalid();
        try {
            URI uri = new URI(repositoryUrl.trim());
            if (!"https".equalsIgnoreCase(uri.getScheme()) || !"github.com".equalsIgnoreCase(uri.getHost())) throw invalid();
            Matcher matcher = PATH.matcher(uri.getPath());
            if (!matcher.matches()) throw invalid();
            return new RepositoryReference(matcher.group(1), matcher.group(2));
        } catch (URISyntaxException exception) {
            throw invalid();
        }
    }

    private ApiException invalid() {
        return new ApiException(HttpStatus.BAD_REQUEST, "Enter a valid public GitHub repository URL, such as https://github.com/owner/repository.");
    }
}
