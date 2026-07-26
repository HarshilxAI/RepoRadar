# API Overview

All API responses use this envelope:

```json
{
  "success": true,
  "message": "Repository analysis completed.",
  "timestamp": "2026-07-16T09:00:00Z",
  "data": {}
}
```

## Analyze a repository

`GET /api/analyze`

Purpose: validates and analyzes one public GitHub repository.

| Query parameter | Required | Description |
| --- | --- | --- |
| `repositoryUrl` | Yes | Complete HTTPS GitHub repository URL, for example `https://github.com/owner/repository`. |

Example:

```text
/api/analyze?repositoryUrl=https%3A%2F%2Fgithub.com%2Fspring-projects%2Fspring-boot
```

On success, `data` contains `repository`, `health`, `metrics`, `languages`, `readme`, `technologies`, `structure`, `activity`, `community`, `snapshot`, `suggestions`, and `analyzedAt`.

```json
{
  "success": true,
  "message": "Repository analysis completed.",
  "timestamp": "2026-07-16T09:00:00Z",
  "data": {
    "repository": { "fullName": "owner/repository", "stars": 42 },
    "health": { "score": 84, "grade": "A", "label": "Healthy Repository" },
    "languages": [{ "name": "Java", "bytes": 120000, "percentage": 83.2 }],
    "suggestions": [{ "title": "Add continuous integration", "priority": "Medium" }]
  }
}
```

Error responses use `success: false`, a safe actionable message, a timestamp, and `data: null`.

| Status | Meaning | Recommended action |
| --- | --- | --- |
| 400 | Malformed or unsupported GitHub URL | Use a complete `https://github.com/owner/repository` URL. |
| 403 | Private or inaccessible repository | Select a public repository. |
| 404 | Repository not found | Check the owner and repository name. |
| 429 | GitHub rate limit reached | Wait for GitHub quota to reset or configure `GITHUB_TOKEN`. |
| 502 | GitHub unavailable or unexpected response | Retry shortly. |
| 504 | GitHub request timed out | Retry shortly. |
| 500 | Unexpected API error | Retry; inspect server logs if it persists. |

## Health check

`GET /health`

Purpose: Render monitoring and deployment verification.

```json
{
  "success": true,
  "message": "RepoRadar API is healthy.",
  "timestamp": "2026-07-16T09:00:00Z",
  "data": { "status": "UP", "version": "1.0.0" }
}
```

