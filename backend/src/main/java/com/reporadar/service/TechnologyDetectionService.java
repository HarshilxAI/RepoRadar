package com.reporadar.service;

import com.reporadar.dto.DashboardDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

@Service
public class TechnologyDetectionService {
    public List<DashboardDto.TechnologyDto> detect(List<String> files, String readme) {
        String corpus = String.join("\n", files).toLowerCase(Locale.ROOT) + "\n" + (readme == null ? "" : readme.toLowerCase(Locale.ROOT));
        List<DashboardDto.TechnologyDto> results = new ArrayList<>();
        add(results, corpus, "Java", "Language", value -> value.contains("pom.xml") || value.contains("build.gradle") || value.contains(".java"), "Java build or source files");
        add(results, corpus, "Spring Boot", "Framework", value -> value.contains("spring-boot") || value.contains("spring boot"), "Spring Boot dependency or configuration");
        add(results, corpus, "Spring MVC", "Framework", value -> value.contains("spring-web") || value.contains("spring mvc"), "Spring web dependency");
        add(results, corpus, "Spring Security", "Framework", value -> value.contains("spring-security") || value.contains("spring security"), "Spring Security dependency");
        add(results, corpus, "Hibernate", "ORM", value -> value.contains("hibernate"), "Hibernate dependency");
        add(results, corpus, "Maven", "Build tool", value -> value.contains("pom.xml"), "pom.xml");
        add(results, corpus, "Gradle", "Build tool", value -> value.contains("build.gradle") || value.contains("gradlew"), "Gradle build file");
        add(results, corpus, "Docker", "DevOps", value -> value.contains("dockerfile") || value.contains("docker-compose"), "Docker configuration");
        add(results, corpus, "React", "Framework", value -> value.contains("react") && (value.contains("package.json") || value.contains(".jsx") || value.contains(".tsx")), "React dependency or source files");
        add(results, corpus, "Next.js", "Framework", value -> value.contains("next.config") || value.contains("nextjs") || value.contains("next.js"), "Next.js configuration");
        add(results, corpus, "Angular", "Framework", value -> value.contains("angular.json") || value.contains("@angular/"), "Angular configuration");
        add(results, corpus, "Vue", "Framework", value -> value.contains("vue.config") || value.contains("@vue/") || value.contains(".vue"), "Vue configuration or source files");
        add(results, corpus, "Node.js", "Runtime", value -> value.contains("package.json"), "package.json");
        add(results, corpus, "Express", "Framework", value -> value.contains("express"), "Express dependency");
        add(results, corpus, "Python", "Language", value -> value.contains("requirements.txt") || value.contains("pyproject.toml") || value.contains(".py"), "Python dependency or source files");
        add(results, corpus, "Flask", "Framework", value -> value.contains("flask"), "Flask dependency");
        add(results, corpus, "FastAPI", "Framework", value -> value.contains("fastapi"), "FastAPI dependency");
        add(results, corpus, "TensorFlow", "Machine learning", value -> value.contains("tensorflow"), "TensorFlow dependency");
        add(results, corpus, "PyTorch", "Machine learning", value -> value.contains("torch"), "PyTorch dependency");
        add(results, corpus, "MongoDB", "Database", value -> value.contains("mongodb") || value.contains("mongoose"), "MongoDB dependency");
        add(results, corpus, "PostgreSQL", "Database", value -> value.contains("postgres") || value.contains("postgresql"), "PostgreSQL dependency");
        add(results, corpus, "MySQL", "Database", value -> value.contains("mysql"), "MySQL dependency");
        add(results, corpus, "Redis", "Database", value -> value.contains("redis"), "Redis dependency");
        add(results, corpus, "Kubernetes", "DevOps", value -> value.contains("kubernetes") || value.contains("k8s") || value.contains(".helm"), "Kubernetes configuration");
        add(results, corpus, "GitHub Actions", "CI/CD", value -> value.contains(".github/workflows"), "GitHub Actions workflow");
        add(results, corpus, "Vercel", "Hosting", value -> value.contains("vercel.json"), "vercel.json");
        add(results, corpus, "Render", "Hosting", value -> value.contains("render.yaml"), "render.yaml");
        add(results, corpus, "Netlify", "Hosting", value -> value.contains("netlify.toml"), "netlify.toml");
        add(results, corpus, "Railway", "Hosting", value -> value.contains("railway"), "Railway configuration");
        add(results, corpus, "Supabase", "Backend platform", value -> value.contains("supabase"), "Supabase configuration or dependency");
        add(results, corpus, "Firebase", "Backend platform", value -> value.contains("firebase"), "Firebase configuration or dependency");
        return results;
    }

    private void add(List<DashboardDto.TechnologyDto> results, String corpus, String name, String category,
                     Predicate<String> condition, String evidence) {
        if (condition.test(corpus)) results.add(new DashboardDto.TechnologyDto(name, category, 90, evidence));
    }
}

