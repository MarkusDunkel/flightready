package com.flightready.weather.dto.meteoblue;

public record MeteoblueWeatherResponse(
        Metadata metadata,
        Units units,
        Multimodel multimodel
) {}