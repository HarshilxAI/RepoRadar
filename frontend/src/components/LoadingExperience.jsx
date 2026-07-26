import { Check, CircleDashed } from 'lucide-react'
import { useEffect, useState } from 'react'

const steps = ['Validating repository', 'Fetching repository data', 'Reading README', 'Detecting technologies', 'Calculating health score', 'Building dashboard']

export default function LoadingExperience() {
  const [active, setActive] = useState(0)
  useEffect(() => { const id = setInterval(() => setActive((step) => Math.min(step + 1, steps.length - 1)), 480); return () => clearInterval(id) }, [])
  return <main className="grid min-h-[70vh] place-items-center px-5"><section className="surface w-full max-w-lg p-7 sm:p-10" aria-live="polite">
    <p className="eyebrow">Repository analysis</p><h1 className="mt-2 text-2xl font-bold text-slate-900">Scanning repository…</h1><p className="mt-2 muted">We’re turning public GitHub signals into a clear health snapshot.</p>
    <div className="mt-8 space-y-4">{steps.map((step, index) => <div key={step} className="flex items-center gap-3 text-sm">
      <span className={`grid h-6 w-6 place-items-center rounded-full ${index < active ? 'bg-teal text-white' : index === active ? 'bg-coral text-white' : 'bg-slate-100 text-slate-400'}`}>{index < active ? <Check size={14} /> : index === active ? <CircleDashed className="animate-spin" size={15} /> : index + 1}</span>
      <span className={index <= active ? 'font-medium text-slate-800' : 'text-slate-400'}>{step}</span>
    </div>)}</div>
    <div className="mt-8 h-2 overflow-hidden rounded-full bg-slate-100"><div className="h-full rounded-full bg-coral transition-all duration-500" style={{ width: `${((active + 1) / steps.length) * 100}%` }} /></div>
  </section></main>
}

