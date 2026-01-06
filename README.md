# FlightReady Monolith

A minimal monolith-style stack with a Vite + React frontend, Spring Boot backend, and PostgreSQL (PostGIS enabled) database.

## Prerequisites

- Docker + Docker Compose

## Quick start

```bash
docker compose up --build
```

### URLs

- Frontend: http://localhost:5173
- Backend API: http://localhost:8080/api/launchsites

## Running outside Docker (optional)

### Backend

```bash
cd backend
mvn spring-boot:run
```

Set the following environment variables as needed:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

### Frontend

```bash
cd frontend
npm install
VITE_PROXY_TARGET=http://localhost:8080 npm run dev
```

The Vite dev server proxies `/api` calls to the backend.

## Project structure

```
/frontend   # Vite + React app
/backend    # Spring Boot app
/docker     # helper scripts/config
```

## Notes

- Database data is stored in a named Docker volume (`db_data`).
- Flyway migrations create the `launchsites` table and enable PostGIS.
