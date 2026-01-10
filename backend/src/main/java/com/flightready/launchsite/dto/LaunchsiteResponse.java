package com.flightready.launchsite.dto;

import java.util.UUID;

public record LaunchsiteResponse(
        UUID id,
        String name,
        Double latitude,
        Double longitude,
        Double directionStart,
        Double directionEnd,
        Integer asl,
        String info
) {}
