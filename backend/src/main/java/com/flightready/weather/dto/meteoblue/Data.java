package com.flightready.weather.dto.meteoblue;

import java.util.List;

public record Data(
        List<String> time,
        List<Double> temperature_spread,
        List<List<Double>> precipitation,
        List<List<Integer>> cloudcover,
        List<List<Double>> temperature,
        List<List<Integer>> winddirection
) {}
