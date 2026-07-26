import { AlertCircle, ArrowLeft, RefreshCw } from 'lucide-react'
import { Link } from 'react-router-dom'

export default function ErrorState({ message, onRetry }) {
  const rateLimit = message.toLowerCase().includes('rate limit')
  return <main className="grid min-h-[70vh] place-items-center px-5"><section className="surface max-w-lg p-8 text-center sm:p-10">
    <span className="mx-auto grid h-12 w-12 place-items-center rounded-full bg-red-50 text-red-700"><AlertCircle /></span>
    <h1 className="mt-5 text-2xl font-bold text-slate-900">{rateLimit ? 'GitHub needs a moment' : 'Analysis unavailable'}</h1>
    <p className="mt-3 muted">{message}</p><p className="mt-3 text-sm text-slate-500">{rateLimit ? 'GitHub limits anonymous API traffic. Wait for the quota to reset, or add a server-side GITHUB_TOKEN for a higher limit.' : 'Check the repository URL and its public visibility, then try again.'}</p>
    <div className="mt-7 flex flex-col justify-center gap-3 sm:flex-row"><button onClick={onRetry} className="inline-flex items-center justify-center gap-2 rounded-lg bg-slate-900 px-4 py-2.5 text-sm font-semibold text-white hover:bg-slate-700"><RefreshCw size={16} />Retry</button><Link to="/" className="inline-flex items-center justify-center gap-2 rounded-lg border border-slate-300 px-4 py-2.5 text-sm font-semibold text-slate-700 hover:bg-slate-50"><ArrowLeft size={16} />Analyze another</Link></div>
  </section></main>
}
