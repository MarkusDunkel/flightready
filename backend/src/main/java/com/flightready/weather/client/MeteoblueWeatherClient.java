package com.flightready.weather.client;

import com.flightready.weather.dto.meteoblue.MeteoblueWeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class MeteoblueWeatherClient {

    private final RestClient restClient;
    private final String apiKey;

    public MeteoblueWeatherClient(RestClient meteoblueWeatherRestClient, @Value("${meteoblue.api-key}") String apiKey) {
        this.restClient = meteoblueWeatherRestClient;
        this.apiKey = apiKey;
    }

    public MeteoblueWeatherResponse fetchCurrentForecast(double latitude, double longitude, int asl) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/packages/multimodel-1h")
                            .queryParam("apikey",
                                    apiKey)
                            .queryParam("lat",
                                    String.valueOf(latitude))
                            .queryParam("lon",
                                    String.valueOf(longitude))
                            .queryParam("asl",
                                    String.valueOf(asl))
                            .queryParam("format",
                                    "json")
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError,
                            (request, response) -> {
                                throw new MeteoblueClientException(
                                        "Meteoblue request failed: " + response.getStatusCode());
                            })
                    .body(MeteoblueWeatherResponse.class);
        } catch (RestClientException ex) {
            // Covers: IO errors, timeouts, AND JSON mapping issues on 200 responses
            throw new MeteoblueClientException("Meteoblue request/parse failed: " + ex.getMessage(),
                    ex);
        }
    }

    public static class MeteoblueClientException extends RuntimeException {
        public MeteoblueClientException(String message) {
            super(message);
        }

        public MeteoblueClientException(String message, Throwable cause) {
            super(message,
                    cause);
        }
    }
}
