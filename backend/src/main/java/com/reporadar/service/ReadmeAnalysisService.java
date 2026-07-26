package com.reporadar.service;

import com.reporadar.dto.DashboardDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ReadmeAnalysisService {
    private static final Map<String, List<String>> CHECKS = Map.ofEntries(
            Map.entry("Installation Guide", List.of("install", "getting started", "setup")),
            Map.entry("Usage Guide", List.of("usage", "how to use", "quick start")),
            Map.entry("Screenshots", List.of("screenshot", "![", "<img")),
            Map.entry("License", List.of("license", "licence")),
            Map.entry("Contributing", List.of("contributing", "contribution")),
            Map.entry("API Documentation", List.of("api reference", "api documentation", "endpoints")),
            Map.entry("Examples", List.of("example", "demo")),
            Map.entry("Badges", List.of("shields.io", "badge")),
            Map.entry("Architecture Diagram", List.of("architecture", "mermaid", "diagram")),
            Map.entry("Table of Contents", List.of("table of contents", "contents")),
            Map.entry("FAQ", List.of("faq", "frequently asked")),
            Map.entry("Changelog", List.of("changelog", "change log"))
    );

    public DashboardDto.ReadmeAnalysisDto analyze(String content, String repositoryDescription) {
        boolean exists = content != null && !content.isBlank();
        String normalized = (content == null ? "" : content).toLowerCase(Locale.ROOT);
        List<DashboardDto.ChecklistItemDto> checklist = new ArrayList<>();
        List<String> strengths = new ArrayList<>();
        List<String> weaknesses = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();
        int present = 0;
        for (Map.Entry<String, List<String>> check : CHECKS.entrySet()) {
            boolean found = check.getValue().stream().anyMatch(normalized::contains);
            checklist.add(new DashboardDto.ChecklistItemDto(check.getKey(), found));
            if (found) present++;
        }
        boolean descriptionPresent = repositoryDescription != null && !repositoryDescription.isBlank();
        checklist.add(new DashboardDto.ChecklistItemDto("Repository Description", descriptionPresent));
        if (descriptionPresent) present++;
        int score = exists ? Math.min(100, 20 + Math.round(present * 80f / checklist.size())) : 0;
        if (exists) strengths.add("A README is available for new contributors and users.");
        if (present >= 5) strengths.add("The documentation covers several practical project topics.");
        if (!exists) weaknesses.add("No README was found, so the project has no central onboarding guide.");
        checklist.stream().filter(item -> !item.present()).limit(3).forEach(item -> {
            weaknesses.add(item.label() + " is not clearly documented.");
            recommendations.add("Add a clear " + item.label().toLowerCase(Locale.ROOT) + " section to make the project easier to adopt.");
        });
        if (!descriptionPresent) recommendations.add("Add a concise repository description so visitors immediately understand its purpose.");
        String summary = !exists ? "The repository does not currently provide a README, which makes onboarding and evaluation difficult."
                : "The README covers " + present + " of " + checklist.size() + " evaluated documentation signals and scores " + score + " out of 100.";
        return new DashboardDto.ReadmeAnalysisDto(score, exists, checklist, strengths, weaknesses, recommendations, summary);
    }
}

