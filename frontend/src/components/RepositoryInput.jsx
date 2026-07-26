import { ArrowRight, Github } from 'lucide-react'
import { useState } from 'react'
import { isValidRepositoryUrl } from '../utils/repository'

export default function RepositoryInput({ onAnalyze, initialValue = '' }) {
  const [value, setValue] = useState(initialValue)
  const [touched, setTouched] = useState(false)
  const valid = isValidRepositoryUrl(value)
  const submit = (event) => { event.preventDefault(); setTouched(true); if (valid) onAnalyze(value.trim()) }
  return <form onSubmit={submit} className="mx-auto w-full max-w-3xl" noValidate>
    <label htmlFor="repository-url" className="sr-only">Public GitHub repository URL</label>
    <div className="rounded-2xl border border-slate-200 bg-white p-2 shadow-card sm:flex sm:items-center">
      <div className="flex flex-1 items-center gap-3 px-3 py-2"><Github className="shrink-0 text-slate-400" size={20} aria-hidden="true" />
        <input id="repository-url" value={value} onChange={(event) => setValue(event.target.value)} onBlur={() => setTouched(true)}
          placeholder="https://github.com/owner/repository" className="min-w-0 flex-1 bg-transparent text-sm text-slate-900 placeholder:text-slate-400 sm:text-base" autoComplete="url" />
      </div>
      <button disabled={!valid} className="mt-1 inline-flex w-full items-center justify-center gap-2 rounded-xl bg-coral px-5 py-3 text-sm font-bold text-white transition hover:bg-[#d95446] disabled:cursor-not-allowed disabled:bg-slate-300 sm:mt-0 sm:w-auto" type="submit">Analyze repository <ArrowRight size={17} /></button>
    </div>
    {touched && value && !valid && <p className="mt-2 text-left text-sm text-red-700" role="alert">Use a complete public GitHub URL, such as https://github.com/owner/repository.</p>}
  </form>
}

