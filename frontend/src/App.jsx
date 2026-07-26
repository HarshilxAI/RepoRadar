import { lazy, Suspense } from 'react'
import { Routes, Route, useNavigate } from 'react-router-dom'
import LandingPage from './pages/LandingPage'

const DashboardPage = lazy(() => import('./pages/DashboardPage'))

export default function App() {
  const navigate = useNavigate()
  const analyze = (repositoryUrl) => navigate(`/dashboard?repository=${encodeURIComponent(repositoryUrl)}`)
  return <Suspense fallback={<main className="grid min-h-screen place-items-center text-sm font-medium text-slate-600">Loading RepoRadar…</main>}><Routes><Route path="/" element={<LandingPage onAnalyze={analyze} />} /><Route path="/dashboard" element={<DashboardPage />} /><Route path="*" element={<LandingPage onAnalyze={analyze} />} /></Routes></Suspense>
}
