package com.flightready.weather.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class MeteoblueWeatherClientConfig {

    @Bean
    RestClient meteoblueWeatherRestClient(
            @Value("${weather.meteoblue.base-url}") String baseUrl,
            @Value("${weather.meteoblue.token}") String apiKey
    ) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .defaultUriVariables(Map.of(
                        "apikey", apiKey,
                        "format", "json"
                ))
                .build();
    }
}
