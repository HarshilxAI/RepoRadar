import { ArrowUpRight, Menu, X } from 'lucide-react'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import Brand from './Brand'
import ThemeToggle from './ThemeToggle'

export default function Header({ dashboard = false }) {
  const [open, setOpen] = useState(false)
  const navigation = dashboard ? [] : [['About', '#about'], ['How It Works', '#how-it-works'], ['Features', '#features'], ['GitHub', 'https://github.com/HarshilxAI']]
  return <header className="sticky top-0 z-30 border-b border-slate-200/70 bg-canvas/95 backdrop-blur dark:border-[#2b2d34]/80 dark:bg-[#101216]/95">
    <nav className="mx-auto flex max-w-7xl items-center justify-between px-5 py-4 lg:px-8" aria-label="Main navigation">
      <Brand />
      <div className="hidden items-center gap-7 md:flex">
        {navigation.map(([label, href]) => <a key={label} href={href} className="text-sm font-medium text-slate-600 transition hover:text-slate-950 dark:text-slate-300 dark:hover:text-white">{label}</a>)}
        <ThemeToggle />
        {dashboard ? <Link to="/" className="rounded-lg bg-slate-900 px-4 py-2 text-sm font-semibold text-white transition hover:bg-slate-700">Analyze another</Link> :
          <a href="#analyze" className="inline-flex items-center gap-1 rounded-lg bg-slate-900 px-4 py-2 text-sm font-semibold text-white transition hover:bg-slate-700">Analyze <ArrowUpRight size={15} /></a>}
      </div>
      <div className="flex items-center gap-2 md:hidden"><ThemeToggle /><button onClick={() => setOpen(!open)} className="rounded-md p-2 text-slate-700 dark:text-slate-200" aria-label="Toggle navigation" aria-expanded={open}>{open ? <X /> : <Menu />}</button></div>
    </nav>
    {open && <div className="border-t border-slate-200 bg-white px-5 py-3 dark:border-[#2b2d34] dark:bg-[#15171b] md:hidden">
      {navigation.map(([label, href]) => <a key={label} onClick={() => setOpen(false)} href={href} className="block rounded-md px-3 py-2 text-sm font-medium text-slate-700 hover:bg-slate-50 dark:text-slate-300 dark:hover:bg-slate-800">{label}</a>)}
      {dashboard ? <Link onClick={() => setOpen(false)} to="/" className="mt-1 block rounded-md bg-slate-900 px-3 py-2 text-sm font-semibold text-white">Analyze another</Link> : <a onClick={() => setOpen(false)} href="#analyze" className="mt-1 block rounded-md bg-slate-900 px-3 py-2 text-sm font-semibold text-white">Analyze repository</a>}
    </div>}
  </header>
}
