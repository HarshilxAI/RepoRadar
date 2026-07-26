package com.reporadar.service;

import com.reporadar.dto.DashboardDto;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class HealthScoreService {
    public DashboardDto.HealthScoreDto calculate(DashboardDto.ReadmeAnalysisDto readme, int structureScore, Instant updatedAt,
                                                  long stars, long forks, int contributors, boolean hasLicense, int releases,
                                                  int technologyCount, boolean hasDescription) {
        int documentation = clamp((readme.score() + (hasDescription ? 100 : 25)) / 2);
        int activity = activityScore(updatedAt);
        int community = clamp((int) Math.round(Math.min(100, stars * 0.45 + forks * 0.8 + contributors * 5)));
        int metadata = (hasLicense ? 50 : 0) + (hasDescription ? 50 : 0);
        int technology = technologyCount == 0 ? 35 : technologyCount >= 3 ? 90 : 65;
        int release = releases == 0 ? 25 : releases >= 3 ? 95 : 65;
        int maintainability = clamp((structureScore + documentation + technology) / 3);
        List<WeightedScore> components = List.of(
                new WeightedScore("Documentation", documentation, 20, "README quality and repository description."),
                new WeightedScore("Repository Activity", activity, 15, "Recent maintenance and commit freshness."),
                new WeightedScore("Project Structure", structureScore, 15, "Source layout and essential project files."),
                new WeightedScore("Community", community, 10, "Contributor and community engagement signals."),
                new WeightedScore("License & Metadata", metadata, 5, "License and repository metadata completeness."),
                new WeightedScore("Technology Best Practices", technology, 10, "Detected build, quality, and deployment tooling."),
                new WeightedScore("Release Management", release, 5, "Published release history."),
                new WeightedScore("Maintainability", maintainability, 10, "Combined documentation, structure, and tooling quality."),
                new WeightedScore("Organization", structureScore, 15, "Folder organization and configuration conventions."));
        int totalWeight = components.stream().mapToInt(component -> component.weight()).sum();
        int score = (int) Math.round(components.stream().mapToDouble(value -> value.score * value.weight).sum() / totalWeight);
        List<DashboardDto.ScoreCategoryDto> breakdown = new ArrayList<>();
        components.forEach(value -> breakdown.add(new DashboardDto.ScoreCategoryDto(value.name, value.score, value.description)));
        String label = score >= 90 ? "Excellent Repository" : score >= 70 ? "Healthy Repository" : score >= 50 ? "Needs Attention" : "Needs Improvement";
        String grade = score >= 90 ? "A+" : score >= 80 ? "A" : score >= 70 ? "B" : score >= 60 ? "C" : score >= 50 ? "D" : "F";
        return new DashboardDto.HealthScoreDto(score, grade, label,
                "This deterministic score combines documentation, activity, organization, community, releases, and maintainability signals.", breakdown);
    }

    private int activityScore(Instant updatedAt) {
        long days = Duration.between(updatedAt, Instant.now()).toDays();
        if (days <= 30) return 100;
        if (days <= 90) return 85;
        if (days <= 180) return 70;
        if (days <= 365) return 50;
        return 25;
    }

    private int clamp(int value) { return Math.max(0, Math.min(100, value)); }
    private record WeightedScore(String name, int score, int weight, String description) { }
}

