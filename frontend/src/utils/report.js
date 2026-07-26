export async function exportReport(data) {
  const { jsPDF } = await import('jspdf')
  const pdf = new jsPDF({ unit: 'pt', format: 'a4' })
  const pageWidth = 595
  const pageHeight = 842
  const margin = 44
  const bottom = 778
  const contentWidth = pageWidth - margin * 2
  let y = 0

  const colors = {
    ink: [15, 23, 42],
    muted: [100, 116, 139],
    teal: [15, 118, 110],
    coral: [239, 107, 91],
    line: [226, 232, 240],
    soft: [248, 250, 252],
  }
  let logoData = null
  try {
    const response = await fetch('/logo.png')
    if (response.ok) {
      const bytes = new Uint8Array(await response.arrayBuffer())
      let binary = ''
      for (let index = 0; index < bytes.length; index += 8192) binary += String.fromCharCode(...bytes.subarray(index, index + 8192))
      logoData = `data:image/png;base64,${btoa(binary)}`
    }
  } catch {
    // The report remains fully usable if the optional brand asset is unavailable.
  }

  const setText = (size, color = colors.ink, style = 'normal') => {
    pdf.setFont('helvetica', style)
    pdf.setFontSize(size)
    pdf.setTextColor(...color)
  }

  const footer = () => {
    pdf.setDrawColor(...colors.line)
    pdf.line(margin, 802, pageWidth - margin, 802)
    setText(8, colors.muted)
    pdf.text('RepoRadar - Scan. Analyze. Improve.', margin, 819)
    pdf.text(`Page ${pdf.getNumberOfPages()}`, pageWidth - margin, 819, { align: 'right' })
  }

  const header = () => {
    if (logoData) pdf.addImage(logoData, 'PNG', margin, 30, 34, 34)
    else {
      pdf.setFillColor(...colors.coral)
      pdf.roundedRect(margin, 30, 34, 34, 9, 9, 'F')
      setText(21, [255, 255, 255], 'bold')
      pdf.text('R', margin + 10, 54)
    }
    setText(20, colors.ink, 'bold')
    pdf.text('RepoRadar', margin + 47, 53)
    setText(9, colors.muted)
    pdf.text('Repository health report', margin + 47, 66)
    y = 105
  }

  const nextPage = () => {
    footer()
    pdf.addPage()
    header()
  }

  const ensure = (height = 24) => {
    if (y + height > bottom) nextPage()
  }

  const textBlock = (text, size = 10, color = colors.muted, style = 'normal', gap = 7) => {
    if (!text) return
    setText(size, color, style)
    const lines = pdf.splitTextToSize(String(text), contentWidth)
    ensure(lines.length * (size + 3) + gap)
    pdf.text(lines, margin, y)
    y += lines.length * (size + 3) + gap
  }

  const heading = (title, eyebrow) => {
    ensure(42)
    if (eyebrow) { setText(8, colors.teal, 'bold'); pdf.text(eyebrow.toUpperCase(), margin, y); y += 14 }
    textBlock(title, 16, colors.ink, 'bold', 13)
  }

  const progress = (value, x, width, color = colors.teal) => {
    pdf.setFillColor(...colors.line)
    pdf.roundedRect(x, y - 8, width, 6, 3, 3, 'F')
    pdf.setFillColor(...color)
    pdf.roundedRect(x, y - 8, Math.max(3, width * Math.max(0, Math.min(100, value)) / 100), 6, 3, 3, 'F')
  }

  const metricGrid = (items) => {
    const gap = 10
    const width = (contentWidth - gap) / 2
    items.forEach((item, index) => {
      if (index % 2 === 0) ensure(52)
      const x = margin + (index % 2) * (width + gap)
      if (index % 2 === 0) {
        pdf.setFillColor(...colors.soft)
        pdf.roundedRect(margin, y - 12, contentWidth, 43, 7, 7, 'F')
      }
      setText(8, colors.muted, 'bold')
      pdf.text(item.label.toUpperCase(), x + 12, y)
      setText(13, colors.ink, 'bold')
      pdf.text(String(item.value), x + 12, y + 18)
      if (index % 2 === 1) y += 52
    })
    if (items.length % 2 === 1) y += 52
  }

  header()
  setText(9, colors.muted)
  pdf.text(data.repository.htmlUrl || '', margin, y)
  y += 17
  textBlock(data.repository.fullName, 24, colors.ink, 'bold', 6)
  textBlock(data.repository.description || 'No repository description provided.', 10, colors.muted, 'normal', 14)

  ensure(92)
  pdf.setFillColor(...colors.soft)
  pdf.roundedRect(margin, y, contentWidth, 84, 10, 10, 'F')
  pdf.setFillColor(...colors.teal)
  pdf.circle(margin + 44, y + 42, 28, 'F')
  setText(17, [255, 255, 255], 'bold')
  pdf.text(String(data.health.score), margin + 44, y + 48, { align: 'center' })
  setText(16, colors.ink, 'bold')
  pdf.text(`${data.health.label} (${data.health.grade})`, margin + 88, y + 34)
  setText(9, colors.muted)
  pdf.text('Overall repository score', margin + 88, y + 50)
  y += 102

  heading('Executive summary', 'At a glance')
  textBlock(data.snapshot.summary, 10)
  metricGrid([
    { label: 'Stars', value: data.metrics.stars },
    { label: 'Forks', value: data.metrics.forks },
    { label: 'Contributors', value: data.metrics.contributors },
    { label: 'Open issues', value: data.metrics.issues },
    { label: 'Open pull requests', value: data.metrics.openPullRequests },
    { label: 'Latest release', value: data.metrics.latestRelease },
  ])

  heading('Health breakdown', 'Deterministic scoring')
  data.health.breakdown.forEach((category) => {
    const description = pdf.splitTextToSize(category.description || '', 390)
    ensure(32 + description.length * 10)
    setText(9, colors.ink, 'bold')
    pdf.text(category.name, margin, y)
    setText(9, colors.muted)
    pdf.text(`${category.score}/100`, margin + 155, y)
    progress(category.score, margin + 205, 250)
    setText(8, colors.muted)
    pdf.text(description, margin, y + 14)
    y += 20 + description.length * 10
  })
  y += 6

  heading('Language analysis', 'Code composition')
  if (!data.languages.length) textBlock('No language data was returned for this repository.', 9)
  data.languages.slice(0, 8).forEach((language) => {
    ensure(23)
    setText(9, colors.ink, 'bold')
    pdf.text(language.name, margin, y)
    setText(9, colors.muted)
    pdf.text(`${language.percentage}%`, margin + 105, y)
    progress(language.percentage, margin + 145, 270, colors.teal)
    y += 19
  })

  heading('README analysis', 'Documentation quality')
  textBlock(data.readme.summary, 9)
  textBlock(`Checklist: ${data.readme.checklist.filter((item) => item.present).length}/${data.readme.checklist.length} signals present`, 9, colors.teal, 'bold', 8)
  if (data.readme.recommendations.length) textBlock(`Recommendations: ${data.readme.recommendations.join(' ')}`, 9)

  heading('Priority recommendations', 'Next actions')
  data.suggestions.forEach((suggestion) => {
    const lines = pdf.splitTextToSize(suggestion.description, contentWidth - 104)
    ensure(Math.max(42, lines.length * 13 + 28))
    pdf.setFillColor(...(suggestion.priority === 'High' ? [254, 242, 242] : suggestion.priority === 'Medium' ? [255, 251, 235] : [240, 253, 250]))
    pdf.roundedRect(margin, y - 12, contentWidth, Math.max(35, lines.length * 13 + 20), 7, 7, 'F')
    setText(8, suggestion.priority === 'High' ? [185, 28, 28] : suggestion.priority === 'Medium' ? [146, 64, 14] : colors.teal, 'bold')
    pdf.text(suggestion.priority.toUpperCase(), margin + 12, y + 2)
    setText(10, colors.ink, 'bold')
    pdf.text(suggestion.title, margin + 74, y + 2)
    setText(9, colors.muted)
    pdf.text(lines, margin + 74, y + 16)
    y += Math.max(35, lines.length * 13 + 20) + 9
  })

  footer()
  pdf.save(`${data.repository.name}-reporadar-report.pdf`)
}
