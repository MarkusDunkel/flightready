package com.flightready.weather.dto.meteoblue;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Metadata(

        @JsonProperty("modelrun_updatetime_utc")
        String modelrunUpdatetimeUtc,

        String name,
        int height,

        @JsonProperty("timezone_abbrevation")
        String timezoneAbbrevation,

        double latitude,

        @JsonProperty("modelrun_utc")
        List<String> modelrunUtc,

        List<String> models,

        @JsonProperty("gridpointelevation")
        List<Integer> gridpointElevation,

        double longitude,

        @JsonProperty("utc_timeoffset")
        double utcTimeOffset,

        @JsonProperty("generation_time_ms")
        double generationTimeMs
) {}
