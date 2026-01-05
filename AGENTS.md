# Task: Implement a Monolith Web App (React/Vite + Java/Spring Boot + Postgres) with Docker Compose

## Goal
Create a single repository that contains:
- Frontend: Vite + React (TypeScript)
- Backend: Java + Spring Boot (REST API)
- Database: PostgreSQL
- Containerized development environment using Docker and docker-compose, with each component running in its own container.

The system must run end-to-end with one command: `docker compose up --build`.

---

## Tech Stack
- Frontend: Vite + React + TypeScript
- Backend: Java 21 + Spring Boot + Maven
- DB: PostgreSQL (latest stable)
- Backend persistence: Spring Data JPA + Flyway (preferred) for migrations
- Docker: multi-container setup via docker-compose

---

## Repository Structure
Create a monorepo structure like:

/frontend
  - Vite React app
/backend
  - Spring Boot app
/docker
  - optional helper scripts/config
docker-compose.yml
README.md

---

## Functional Requirements (Minimal App)
Implement a simple CRUD domain to prove end-to-end functionality:
- Entity: `launchsites`
  - id (UUID)
  - name (string, required)
  - location geography(Point, 4326), required
  - direction_start (number, required)
  - direction_end (number, required)
  - info (string)

### Backend API (REST)
Base path: `/api`
Endpoints:
- GET `/api/launchsites` -> list all
- POST `/api/launchsites` -> create
- PUT `/api/launchsites/{id}` -> update 
- DELETE `/api/launchsites/{id}` -> delete

Validation:
- name must not be blank
Return JSON, appropriate status codes, and basic error handling.

### Frontend UI
Pages/components:
- A single page that lists launchsites, allows:
  - create a launchsite
  - delete a launchsite

Frontend must call the backend container via a proxy so dev works cleanly (Vite proxy recommended).

---

## Database Requirements
- Use PostgreSQL in its own container.
- Backend connects via environment variables.
- Use Flyway migrations to create the `launchsites` table.
- Ensure the DB data persists with a named Docker volume.

---

## Docker / Compose Requirements
Create a `docker-compose.yml` that defines 3 services:
1) `db` (postgres)
2) `backend` (spring boot)
3) `frontend` (vite dev server OR built static + served)

Requirements:
- Each service in separate container.
- Use a Docker network (default is fine).
- Use healthcheck for Postgres and ensure backend waits for DB readiness (depends_on + healthcheck or retry logic).
- Expose ports:
  - frontend: 5173 -> host 5173
  - backend: 8080 -> host 8080
  - db: optional exposure (can omit host port if not needed)
- Use `.env` (or compose env vars) for credentials; include a safe default for local dev.

### Development Mode Preference
Use dev servers in containers:
- Frontend runs `npm install` (or pnpm) then `npm run dev -- --host 0.0.0.0`
- Backend runs `mvn spring-boot:run`
Mount source code as volumes for hot reload:
- frontend: mount `./frontend:/app`
- backend: mount `./backend:/app`
Also handle node_modules properly (named volume or container-local install to avoid permission issues).

---

## Backend Implementation Details
- Spring Boot app name: e.g. `monolith-app`
- Dependencies: Spring Web, Spring Data JPA, Validation, Flyway, PostgreSQL Driver
- Config via `application.yml` using env vars:
  - SPRING_DATASOURCE_URL
  - SPRING_DATASOURCE_USERNAME
  - SPRING_DATASOURCE_PASSWORD
- Enable CORS for dev OR rely on Vite proxy (preferred: Vite proxy, minimal CORS config)

---

## Frontend Implementation Details
- Use TypeScript, functional components, hooks.
- Create a small API client file (fetch-based).
- Handle loading/error states.
- Configure Vite dev proxy:
  - Requests to `/api` proxy to `http://backend:8080` when running in compose.
  - Also allow running frontend outside docker by pointing to `http://localhost:8080` (document in README).

---

## Acceptance Criteria
- `docker compose up --build` starts all services successfully.
- Opening `http://localhost:5173` shows the UI.
- Creating/updating/deleting todos works and persists in Postgres.
- Database schema is created via Flyway migration automatically.
- Repo includes a clear `README.md` with:
  - prerequisites
  - how to start/stop
  - how to run backend/frontend outside docker (optional)
  - key URLs/ports

---

## Implementation Notes / Constraints
- Keep it simple and production-ish (clean structure, but minimal).
- Use UUIDs in backend + DB.
- Provide sensible error responses (at least for validation errors).
- Ensure containers run as non-root when feasible (nice-to-have, not required).

Deliverables:
- Full repo layout as described
- docker-compose.yml
- Dockerfiles for frontend and backend
- Flyway migration
- Working CRUD end-to-end
