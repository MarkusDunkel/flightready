package com.flightready.weather.dto.meteoblue;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Multimodel(
        @JsonProperty("data_1h")
        Data data
) {}
