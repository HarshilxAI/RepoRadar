package com.reporadar.utils;

import com.reporadar.exception.ApiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RepositoryUrlParserTest {
    private final RepositoryUrlParser parser = new RepositoryUrlParser();

    @Test void parsesGithubUrl() {
        var reference = parser.parse("https://github.com/spring-projects/spring-boot");
        assertEquals("spring-projects", reference.owner());
        assertEquals("spring-boot", reference.name());
    }

    @Test void rejectsNonRepositoryUrl() {
        assertThrows(ApiException.class, () -> parser.parse("https://gitlab.com/group/repository"));
    }
}

