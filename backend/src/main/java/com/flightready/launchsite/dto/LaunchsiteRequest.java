package com.flightready.launchsite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LaunchsiteRequest(

        @NotBlank
        String name,

        @NotNull
        Double latitude,

        @NotNull
        Integer asl,

        @NotNull
        Double longitude,

        @NotNull
        Double directionStart,

        @NotNull
        Double directionEnd,

        String info

) {}
