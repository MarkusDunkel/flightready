package com.flightready.weather.api;

import com.flightready.weather.service.WeatherIngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/api/weather-sync")
public class WeatherSyncController {

    private final WeatherIngestionService weatherIngestionService;

    public WeatherSyncController(WeatherIngestionService weatherIngestionService) {
        this.weatherIngestionService = weatherIngestionService;
    }

    /**
     * Triggers one sync cycle manually.
     * POST /api/weather-sync
     */
    @PostMapping
    public ResponseEntity<SyncResponse> triggerSync() {
        int processed = weatherIngestionService.syncOnce(); // returns number of items processed (per your earlier example)
        return ResponseEntity.accepted().body(new SyncResponse(processed, OffsetDateTime.now(ZoneOffset.UTC)));
    }

    public record SyncResponse(int processedItems, OffsetDateTime triggeredAt) {}
}
