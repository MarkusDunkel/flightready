package com.flightready.launchsite.dto;

import java.util.UUID;

public record LaunchsiteResponse(
        UUID id,
        String name,
        double latitude,
        double longitude,
        double directionStart,
        double directionEnd,
        int asl,
        String info
) {}
