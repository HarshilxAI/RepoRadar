package com.reporadar.controller;

import com.reporadar.dto.ApiResponse;
import com.reporadar.dto.DashboardDto;
import com.reporadar.service.RepositoryAnalysisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AnalysisController {
    private final RepositoryAnalysisService analysisService;

    public AnalysisController(RepositoryAnalysisService analysisService) { this.analysisService = analysisService; }

    @GetMapping("/analyze")
    public ApiResponse<DashboardDto> analyze(@RequestParam(required = false) String repositoryUrl) {
        return ApiResponse.success("Repository analysis completed.", analysisService.analyze(repositoryUrl));
    }
}

