package com.reporadar.dto;

import java.time.Instant;
import java.util.List;

public record DashboardDto(
        RepositoryDto repository,
        HealthScoreDto health,
        QuickMetricsDto metrics,
        List<LanguageDto> languages,
        ReadmeAnalysisDto readme,
        List<TechnologyDto> technologies,
        StructureInsightDto structure,
        ActivityDto activity,
        CommunityDto community,
        DeveloperSnapshotDto snapshot,
        List<SuggestionDto> suggestions,
        Instant analyzedAt
) {
    public record RepositoryDto(String name, String fullName, String owner, String ownerAvatarUrl, String description,
                                String htmlUrl, String cloneUrl, String defaultBranch, String license, String visibility,
                                String createdAt, String updatedAt, long stars, long forks, long watchers, long openIssues) { }
    public record HealthScoreDto(int score, String grade, String label, String explanation, List<ScoreCategoryDto> breakdown) { }
    public record ScoreCategoryDto(String name, int score, String description) { }
    public record QuickMetricsDto(long stars, long forks, long issues, long watchers, int contributors, long sizeKb,
                                  int openPullRequests, String latestRelease, String latestCommit) { }
    public record LanguageDto(String name, long bytes, double percentage, String color) { }
    public record ReadmeAnalysisDto(int score, boolean exists, List<ChecklistItemDto> checklist, List<String> strengths,
                                    List<String> weaknesses, List<String> recommendations, String summary) { }
    public record ChecklistItemDto(String label, boolean present) { }
    public record TechnologyDto(String name, String category, int confidence, String evidence) { }
    public record StructureInsightDto(int score, List<String> goodPractices, List<String> missingFiles,
                                      List<String> improvements, List<String> topLevelFolders) { }
    public record ActivityDto(String latestCommit, String lastUpdated, int repositoryAgeDays, int releases,
                              int branches, int contributors, String commitFrequency, boolean inactive) { }
    public record CommunityDto(long stars, long forks, long watchers, int contributors, long issues, int score, boolean discussionsAvailable) { }
    public record DeveloperSnapshotDto(String grade, String summary, List<String> strengths, List<String> needsImprovement) { }
    public record SuggestionDto(String title, String description, String priority, String reason, String expectedBenefit) { }
}

