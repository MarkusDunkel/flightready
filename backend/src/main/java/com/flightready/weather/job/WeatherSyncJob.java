package com.flightready.weather.job;

import com.flightready.weather.service.WeatherIngestionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Runs a single weather sync cycle every day at midnight.
 *
 * Note: scheduling must be enabled via @EnableScheduling on a @Configuration / @SpringBootApplication class.
 */
@Component
public class WeatherSyncJob {

    private final WeatherIngestionService weatherIngestionService;

    public WeatherSyncJob(WeatherIngestionService weatherIngestionService) {
        this.weatherIngestionService = weatherIngestionService;
    }

    /**
     * Daily at 00:00 (midnight) server time.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void pullDailyAtMidnight() {
        weatherIngestionService.syncOnce();
    }
}
