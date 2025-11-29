📊 Exec Dashboard

A multi-tenant Kafka platform dashboard built with Spring Boot + React

This project provides an executive-level dashboard for Kafka-as-a-Platform, displaying:
•	Onboarded tenants
•	Topics & metadata
•	Real-time Kafka metrics (producer rate, consumer lag, cluster health)
•	Historical metrics (via New Relic)
•	SLAs, latency, and usage patterns

The project is designed as a monolith for simplicity, with clean modular separation:

exec-dashboard/
├── backend/   → Spring Boot 3.x + Java 21 + GraphQL + REST
├── frontend/  → React + Vite + TypeScript
└── pom.xml     → Maven multi-module parent

Frontend is built and packaged inside the backend JAR for a single deployable artifact.

⸻

🏗️ Architecture Overview

          ┌─────────────────────────────────────┐
          │               Frontend               │
          │    React + Vite + TS + ShadCN UI     │
          └─────────────────────────────────────┘
                          │
            (Bundled into Spring Boot static/)
                          │
          ┌─────────────────────────────────────┐
          │               Backend                │
          │ Spring Boot: REST + GraphQL + WebFlux│
          │ Kafka Client (realtime metrics)      │
          │ NewRelic API (historical metrics)    │
          │ Couchbase Client (metadata)          │
          └─────────────────────────────────────┘
                          │
                          ▼
                Kubernetes (AKS) Deployment

Key Characteristics:
•	Monolith build (backend bundles frontend dist)
•	Multi-module Maven repo
•	React dev server proxy + production static packaging
•	Docker multi-stage build
•	Ready for AKS deployment

⸻

🛠️ Tech Stack

Backend
•	Java 21
•	Spring Boot 3.x
•	Spring WebFlux
•	Spring GraphQL
•	Kafka Client 4.x
•	Couchbase Java SDK
•	NewRelic API integration
•	Maven multi-module build

Frontend
•	React 18
•	TypeScript
•	Vite
•	Tailwind CSS (optional)
•	ShadCN/UI (optional)
•	React Query (optional)

DevOps
•	Docker (multi-stage)
•	AKS-ready deployment model
•	Maven wrapper
•	Single JAR deployment

⸻

📁 Project Structure

exec-dashboard/
│
├── README.md                 → This file
├── .gitignore                → Global repository ignores
├── pom.xml                   → Parent Maven POM (packaging=pom)
│
├── backend/
│   ├── pom.xml               → Backend module POM
│   ├── Dockerfile            → Multi-stage Docker build
│   └── src/                  → Java source
│
└── frontend/
├── package.json          → React/Vite config
├── vite.config.ts
└── src/                  → UI source


⸻

🧩 Local Development Guide

1️⃣ Install Requirements
•	JDK 21+
•	Maven 3.9+
•	Node 20+ (optional — Maven installs Node automatically)
•	Docker Desktop (optional)

⸻

🧪 Local Development (DEV Mode)

Frontend and backend run separately for the best developer experience.

▶ Start backend (with mock data)

cd backend
mvn spring-boot:run

Backend runs at:

👉 http://localhost:8080

⸻

▶ Start frontend

cd frontend
npm install
npm run dev

Frontend runs at:

👉 http://localhost:5173

The dev proxy sends API calls to Spring Boot.

⸻

📦 Build Full Monolith (Frontend + Backend)

Build only backend module (it builds frontend internally):

mvn -pl backend clean package

The final artifact is:

backend/target/backend-1.0-SNAPSHOT.jar

This JAR contains:
•	Spring Boot app
•	React build (dist/)
•	Static assets

⸻

🚀 Run Monolith from JAR

java -jar backend/target/backend-1.0-SNAPSHOT.jar

UI available at:

👉 http://localhost:8080

API test:

curl http://localhost:8080/api/realtime/cluster


⸻

🐳 Docker Build & Run

1. Build Docker image

Run inside the backend/ directory:

```
docker build -t exec-dashboard:latest .
```

2. Run the container

```
docker run -p 8080:8080 exec-dashboard:latest
```


Validate:

curl http://localhost:8080/api/realtime/cluster


⸻

☸ Deploying to AKS (Optional Outline)
1.	Push image to ACR:

az acr login --name <registry>
docker tag exec-dashboard:latest <registry>.azurecr.io/exec-dashboard:1.0.0
docker push <registry>.azurecr.io/exec-dashboard:1.0.0


	2.	Kubernetes manifests:

deployment.yaml
service.yaml
ingress.yaml
configmap.yaml


	3.	Apply to AKS:

kubectl apply -f k8s/



(We can generate full AKS-ready YAMLs for you.)

⸻

🧱 Build Flow Summary

Local Dev:

React dev server → localhost:5173
Backend → localhost:8080

Production Build:

Maven (backend module) → build frontend using Node → copy dist → create boot jar

Docker:

Multi-stage → Maven Build → JAR → JRE image

Deployment:

Single Docker container, no separate frontend hosting


------
🚀 Executive Dashboard — Tech Stack & Design Choices

🟩 1. Architecture Style

✔ Monolithic Application (Recommended)

Backend (Spring Boot) + Frontend (React) packaged and deployed together.

Why?
•	Simplest build/deploy
•	No CORS issues
•	Easier SSO integration
•	One artifact, one service
•	Best for internal tooling
•	Easy mock-mode integration
•	Works well inside AKS

⸻

🟦 2. Frontend Stack

✔ React + TypeScript

Modern, safe, enterprise-grade.

✔ UI Library: ShadCN UI
•	Tailwind-based
•	Clean, modern, enterprise-ready
•	Better than MUI for dashboards
•	Highly customizable

✔ Styling: Tailwind CSS

✔ Charting Library: Apache ECharts

Why ECharts?
•	Best for metrics dashboards
•	Handles large datasets
•	Smooth interactions (hover, zoom, drill-down)
•	Used by real analytics apps (Alibaba, Uber, Cloudflare)
•	More powerful than Chart.js

✔ Data Fetching: React Query (TanStack Query)

Handles polling, caching, retries, query invalidation beautifully.

✔ Pages-based Routing (React Router)

⸻

🟧 3. Backend Stack

✔ Spring Boot 3.x (Java 21)

Latest & modern JVM.

✔ Hybrid API Approach
•	REST API for real-time Kafka metrics
•	GraphQL API for historical + metadata queries

Why hybrid?

Use Case	Best Approach	Why
Real-time Kafka metrics	REST	Reliable polling, low latency
Tenant/Topic metadata	GraphQL	Flexible queries
Historical charts	GraphQL	Complex aggregation


⸻

🟥 4. Data Sources (3 streams)

1️⃣ Real-time Operational Metrics → Kafka AdminClient

Direct lightweight polling over Kafka binary protocol.

Includes:
•	messages/sec (offset delta)
•	consumer lag
•	partition health
•	ISR status
•	topic size
•	leader/follower info

Fastest. Lightweight. No JMX. No NewRelic.

⸻

2️⃣ Historical Metrics → NewRelic NRQL API

Use company’s existing investment.

Includes:
•	last 7 days topic throughput
•	daily message count
•	latency breach counts
•	partition under-replicated history

No separate DB needed.

⸻

3️⃣ Metadata → Couchbase

Source of truth for:
•	tenant → topics mapping
•	serviceId
•	systemId
•	userIds
•	ACLs
•	SLA tiers (Gold/Platinum)
•	DLQ / Retry topic classification

Kafka cannot provide this — mandatory for executive view.

⸻

🟨 5. Real-Time Data Delivery (to UI)

✔ REST Polling (NOT WebSockets)

Frontend polls API every 2–5 seconds.

Backend polls Kafka on-demand or keeps a small 1–2 second cache.

Why not WebSockets?
•	Kafka does not push events
•	Metrics are snapshots
•	Polling is standard for dashboards
•	Redpanda Console, Kafka UI, Confluent Control Center → ALL use polling
•	Simplifies deployment on AKS ingress

⸻

🟪 6. Backend Architecture (Clean Layering)

/controllers
- RealTimeController   (REST)
- GraphQLController    (GraphQL)
/services
- RealTimeMetricService
- HistoricalMetricService
- MetadataService
/adapters
/kafka
- KafkaAdminClientAdapter
/newrelic
- NewRelicClient
/couchbase
- MetadataRepository
/config
/models


⸻

🟫 7. Mock-Mode Support

Key Requirement: Build UI now, plug real data later

Add an .env or config:

DATA_SOURCE_MODE=MOCK

When in MOCK:
•	KafkaAdminClient adapter returns mock real-time JSON
•	NewRelic adapter returns mock historical JSON
•	Couchbase adapter returns mock tenant metadata

This allows:
•	UI to be fully functional
•	Backend to have placeholder stubs
•	CI/CD to run without dependencies

Switch to REAL mode upon integration.

⸻

🟩 8. Deployment

Platform: Azure AKS

Ingress: NGINX Ingress Controller

Docker: One container (monolith)

Ports:
•	Backend REST/GraphQL on 8080
•	UI served from /

Security/Future Needs:
•	SSO (Azure AD) can be added later
•	TLS at ingress
•	Config via ConfigMaps + Secrets

⸻

🟦 9. Summary — FINAL Blueprint

✔ Monolith

✔ React + ShadCN + Tailwind + ECharts

✔ Spring Boot (REST + GraphQL Hybrid)

✔ Real-time → Kafka AdminClient

✔ Historical → NewRelic

✔ Metadata → Couchbase

✔ Polling → 2–5 seconds

✔ Mock-mode enabled

✔ Deployed on AKS

This is modern, lightweight, enterprise-grade, and future-proof.

