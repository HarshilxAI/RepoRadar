/** @type {import('tailwindcss').Config} */
export default {
  darkMode: 'class',
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        canvas: '#fdfbf7',
        ink: '#1d2939',
        coral: '#ef6b5b',
        teal: '#0f766e',
        sky: '#0284c7',
        amber: '#d97706',
      },
      boxShadow: { card: '0 8px 24px rgb(15 23 42 / 0.07)' },
      fontFamily: { sans: ['Inter', 'ui-sans-serif', 'system-ui', 'sans-serif'] },
    },
  },
  plugins: [],
}
