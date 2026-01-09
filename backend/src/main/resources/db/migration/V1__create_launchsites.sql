CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS launchsite (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    location geography(Point,4326) NOT NULL,
    direction_start DOUBLE PRECISION NOT NULL,
    direction_end DOUBLE PRECISION NOT NULL,
    asl INTEGER NOT NULL,
    info TEXT
);
