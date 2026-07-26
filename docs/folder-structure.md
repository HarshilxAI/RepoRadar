# Folder Structure

```text
RepoRadar/
├── backend/
│   ├── src/main/java/com/reporadar/
│   │   ├── client/          # GitHub REST boundary
│   │   ├── config/          # properties and CORS
│   │   ├── controller/      # API endpoints
│   │   ├── dto/             # response contracts
│   │   ├── exception/       # safe exception handling
│   │   ├── model/           # internal domain records
│   │   ├── service/         # analysis engines
│   │   └── utils/           # parsing helpers
│   ├── src/main/resources/  # application configuration
│   └── pom.xml
├── frontend/
│   ├── src/components/      # shared presentational components
│   ├── src/pages/           # routed pages
│   ├── src/services/        # backend client
│   ├── src/styles/          # Tailwind entry stylesheet
│   └── src/utils/           # formatting and PDF export
├── docs/
├── render.yaml
├── README.md
└── LICENSE
```

