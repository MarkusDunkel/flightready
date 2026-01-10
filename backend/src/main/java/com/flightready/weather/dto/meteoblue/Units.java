package com.flightready.weather.dto.meteoblue;

public record Units(
        String precipitation,
        String windspeed,
        String cloudcover,
        String radiation,
        String time,
        String temperature,
        String relativehumidity,
        String winddirection
) {}
