CREATE TABLE IF NOT EXISTS weather (
    id UUID PRIMARY KEY,
    provider TEXT NOT NULL,
    schema_version TEXT NOT NULL DEFAULT 'v1',
    launchsite_id UUID NOT NULL,
    payload JSONB NOT NULL,
    fetched_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT fk_weather_launchsite
        FOREIGN KEY (launchsite_id)
        REFERENCES launchsite(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_weather_location_fetched
ON weather (launchsite_id, fetched_at DESC);