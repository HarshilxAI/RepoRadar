Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

Set-Location "$PSScriptRoot\backend"

if (-not (Test-Path '.\mvnw.cmd')) {
  throw 'backend/mvnw.cmd is missing.'
}

Write-Host 'Starting RepoRadar backend on http://localhost:8080 ...' -ForegroundColor Cyan
.\mvnw.cmd spring-boot:run
