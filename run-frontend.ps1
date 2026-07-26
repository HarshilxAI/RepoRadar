Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

Set-Location "$PSScriptRoot\frontend"

if (-not (Test-Path '.\node_modules')) {
  Write-Host 'Installing frontend dependencies...' -ForegroundColor Yellow
  npm install
}

Write-Host 'Starting RepoRadar frontend...' -ForegroundColor Cyan
npm run dev
