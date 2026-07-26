import { ArrowUpRight, BarChart3, BookOpenCheck, Code2, Github, Linkedin, Radar, ShieldCheck, Sparkles, Zap } from 'lucide-react'
import Header from '../components/Header'
import RepositoryInput from '../components/RepositoryInput'

const features = [
  [Radar, 'Repository Health', 'A transparent score that makes repository quality easy to understand.'],
  [BookOpenCheck, 'README Analysis', 'Spot onboarding gaps before users and contributors do.'],
  [Code2, 'Technology Detection', 'Identify frameworks, tooling, platforms, and project signals.'],
  [BarChart3, 'Visual Analytics', 'Explore languages, activity, metrics, and concrete next steps.'],
]

const steps = [
  [Github, 'Paste Repository', 'Share a public GitHub URL.'],
  [Zap, 'Scan Repository', 'We fetch the relevant signals.'],
  [BarChart3, 'Analyze Metrics', 'Our deterministic engine evaluates health.'],
  [Sparkles, 'Improve Your Project', 'Leave with focused next actions.'],
]

export default function LandingPage({ onAnalyze }) {
  return <>
    <Header />
    <main>
      <section className="px-5 pb-16 pt-20 sm:pb-24 sm:pt-28"><div className="mx-auto max-w-5xl text-center">
        <p className="eyebrow">Scan. Analyze. Improve.</p>
        <h1 className="mx-auto mt-5 max-w-4xl text-4xl font-extrabold tracking-[-0.04em] text-slate-950 sm:text-6xl">Know Your Repository Better.</h1>
        <p className="mx-auto mt-6 max-w-2xl text-base leading-7 text-slate-600 sm:text-lg">Analyze any public GitHub repository in seconds and receive professional insights, repository health scores, documentation analysis, and actionable recommendations.</p>
        <div id="analyze" className="mt-10 scroll-mt-28"><RepositoryInput onAnalyze={onAnalyze} /><a href="https://github.com/spring-projects/spring-boot" className="mt-3 inline-block text-sm font-medium text-teal underline-offset-4 hover:underline">Try an example: spring-projects/spring-boot</a></div>
        <div className="mt-7 flex flex-wrap justify-center gap-x-6 gap-y-2 text-xs font-medium text-slate-500"><span>No sign-up required.</span><span>No data stored.</span><span>Powered by GitHub Public APIs.</span></div>
      </div></section>

      <section id="about" className="border-y border-slate-200/80 bg-white px-5 py-16 dark:bg-[#15171b] sm:py-20"><div className="mx-auto grid max-w-7xl gap-8 lg:grid-cols-[1fr_1.5fr] lg:items-end"><div><p className="eyebrow">About RepoRadar</p><h2 className="section-title mt-2">Repository context, without the noise.</h2></div><p className="max-w-2xl text-base leading-7 text-slate-600 dark:text-slate-300">RepoRadar turns public GitHub signals into a calm, decision-ready view of repository quality. Understand what is working, what needs attention, and where your next hour will have the most impact.</p></div></section>

      <section id="features" className="border-b border-slate-200/80 bg-white px-5 py-16 dark:bg-[#111317] sm:py-20"><div className="mx-auto max-w-7xl"><div className="max-w-xl"><p className="eyebrow">Designed for clarity</p><h2 className="section-title mt-2">Everything you need to see what matters.</h2></div><div className="mt-10 grid gap-5 sm:grid-cols-2 lg:grid-cols-4">{features.map(([Icon, title, text]) => <article key={title} className="rounded-2xl border border-slate-200 p-6 transition duration-200 hover:-translate-y-1 hover:shadow-card dark:border-[#2b2d34] dark:bg-[#1a1b20]"><span className="grid h-10 w-10 place-items-center rounded-lg bg-orange-50 text-coral dark:bg-[#2a2119]"><Icon size={20} /></span><h3 className="mt-5 font-bold text-slate-900 dark:text-white">{title}</h3><p className="mt-2 text-sm leading-6 text-slate-600 dark:text-slate-300">{text}</p></article>)}</div></div></section>

      <section className="px-5 py-16 sm:py-20"><div className="mx-auto grid max-w-7xl gap-8 rounded-3xl bg-slate-900 px-7 py-10 text-white dark:bg-[#1a1b20] dark:ring-1 dark:ring-[#36343a] sm:grid-cols-3 sm:px-12"><div><p className="text-4xl font-extrabold">100%</p><p className="mt-1 text-sm text-slate-300">Public GitHub data</p></div><div><p className="text-4xl font-extrabold">Seconds</p><p className="mt-1 text-sm text-slate-300">From URL to insight</p></div><div><p className="text-4xl font-extrabold">0</p><p className="mt-1 text-sm text-slate-300">Accounts or stored reports</p></div></div></section>

      <section id="how-it-works" className="bg-white px-5 py-16 dark:bg-[#15171b] sm:py-20"><div className="mx-auto max-w-7xl"><p className="eyebrow">How it works</p><h2 className="section-title mt-2">A better repository review in four steps.</h2><div className="mt-10 grid gap-7 md:grid-cols-4">{steps.map(([Icon, title, text], index) => <article key={title} className="relative"><span className="grid h-11 w-11 place-items-center rounded-full bg-teal text-sm font-bold text-white">{index + 1}</span><Icon className="mt-5 text-teal" size={22} /><h3 className="mt-3 font-bold text-slate-900 dark:text-white">{title}</h3><p className="mt-2 text-sm leading-6 text-slate-600 dark:text-slate-300">{text}</p></article>)}</div></div></section>
    </main>
    <footer className="border-t border-slate-200 bg-canvas px-5 py-9 dark:border-[#2b2d34]"><div className="mx-auto flex max-w-7xl flex-col gap-7 text-sm text-slate-500"><div className="flex flex-col justify-between gap-4 sm:flex-row sm:items-center"><span className="inline-flex items-center gap-2 font-semibold text-slate-700 dark:text-slate-200"><ShieldCheck size={16} className="text-teal" />RepoRadar v1.0</span><span>Made with Java + React</span><a className="hover:text-slate-800 dark:hover:text-white" href="https://github.com/HarshilxAI" target="_blank" rel="noreferrer">GitHub</a><span>Rights Reserved by Harshil Gurjar</span></div><div className="flex items-center justify-center gap-3 border-t border-slate-200 pt-5 dark:border-[#2b2d34]"><span className="text-xs font-semibold uppercase tracking-wider text-slate-400">Connect</span><a aria-label="LinkedIn" title="LinkedIn" href="https://www.linkedin.com/in/harshil-gurjar23/" target="_blank" rel="noreferrer" className="grid h-8 w-8 place-items-center rounded-lg border border-slate-200 transition hover:border-teal hover:text-teal dark:border-[#36343a]"><Linkedin size={15} /></a><a aria-label="GitHub" title="GitHub" href="https://github.com/HarshilxAI" target="_blank" rel="noreferrer" className="grid h-8 w-8 place-items-center rounded-lg border border-slate-200 transition hover:border-teal hover:text-teal dark:border-[#36343a]"><Github size={15} /></a><a aria-label="Portfolio" title="Portfolio" href="https://hdgurjar.netlify.app/" target="_blank" rel="noreferrer" className="grid h-8 w-8 place-items-center rounded-lg border border-slate-200 transition hover:border-teal hover:text-teal dark:border-[#36343a]"><ArrowUpRight size={15} /></a></div></div></footer>
  </>
}

