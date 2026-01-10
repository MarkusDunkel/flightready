package com.flightready.weather.service;
import com.flightready.launchsite.Launchsite;
import com.flightready.launchsite.LaunchsiteRepository;
import com.flightready.weather.Weather;
import com.flightready.weather.WeatherRepository;
import com.flightready.weather.client.MeteoblueWeatherClient;
import com.flightready.weather.dto.meteoblue.MeteoblueWeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class WeatherIngestionService {

    private static final int DEFAULT_ASL = 171;

    private final LaunchsiteRepository launchsiteRepo;
    private final MeteoblueWeatherClient meteoblueWeatherClient;
    private final WeatherRepository weatherRepo;

    public WeatherIngestionService(
            LaunchsiteRepository launchsiteRepo,
            MeteoblueWeatherClient meteoblueWeatherClient,
            WeatherRepository weatherRepo
    ) {
        this.launchsiteRepo = launchsiteRepo;
        this.meteoblueWeatherClient = meteoblueWeatherClient;
        this.weatherRepo = weatherRepo;
    }

    public UUID fetchAndStore(UUID launchsiteId) {
        var site = launchsiteRepo.findById(launchsiteId)
                .orElseThrow(() -> new IllegalArgumentException("Launchsite not found: " + launchsiteId));

        var point = site.getLocation();
        double longitude = point.getX();
        double latitude  = point.getY();

        MeteoblueWeatherResponse payload =
                meteoblueWeatherClient.fetchCurrentForecast(latitude, longitude, DEFAULT_ASL);

        Weather w = Weather.meteoblue(site, payload);
        weatherRepo.save(w);

        return w.getId();
    }

    @Transactional
    public int syncOnce() {
        List<UUID> ids = launchsiteRepo.findAll()
                .stream()
                .map(Launchsite::getId)
                .toList();

        ids.forEach(this::fetchAndStore);
        return ids.size();
    }
}
