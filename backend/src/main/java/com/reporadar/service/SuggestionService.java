package com.reporadar.service;

import com.reporadar.dto.DashboardDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class SuggestionService {
    public List<DashboardDto.SuggestionDto> generate(DashboardDto.ReadmeAnalysisDto readme, List<String> files,
                                                       boolean hasLicense, int releases, boolean hasWorkflow) {
        String content = String.join("\n", files).toLowerCase(Locale.ROOT);
        List<DashboardDto.SuggestionDto> suggestions = new ArrayList<>();
        if (!hasLicense) suggestions.add(item("Add an open-source license", "Include a LICENSE file and state the license in the README.", "High", "No license was detected.", "Clarifies how others can use and contribute to the project."));
        if (!readme.exists() || readme.score() < 70) suggestions.add(item("Strengthen the README", "Add installation, usage, examples, and project context sections.", "High", "README analysis found important onboarding gaps.", "Helps visitors evaluate and start using the project quickly."));
        if (releases == 0) suggestions.add(item("Publish a first release", "Create a tagged release with concise release notes.", "Medium", "No GitHub releases were found.", "Makes stable versions easier to discover and consume."));
        if (!hasWorkflow) suggestions.add(item("Add continuous integration", "Create a GitHub Actions workflow that builds and tests pull requests.", "Medium", "No GitHub Actions workflow was detected.", "Catches regressions early and signals healthy maintenance."));
        if (!content.contains("test") && !content.contains("spec")) suggestions.add(item("Add automated tests", "Introduce focused unit tests for the most important logic.", "Medium", "No clear test files or folders were detected.", "Improves confidence when the project changes."));
        if (suggestions.isEmpty()) suggestions.add(item("Keep the quality bar high", "Maintain the documentation, release cadence, and automated checks already present.", "Low", "The repository shows strong foundational practices.", "Preserves maintainability as the project grows."));
        return suggestions;
    }

    private DashboardDto.SuggestionDto item(String title, String description, String priority, String reason, String benefit) {
        return new DashboardDto.SuggestionDto(title, description, priority, reason, benefit);
    }
}

