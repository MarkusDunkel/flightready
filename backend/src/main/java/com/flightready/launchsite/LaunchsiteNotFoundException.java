package com.flightready.launchsite;

import java.util.UUID;

public class LaunchsiteNotFoundException extends RuntimeException {
    public LaunchsiteNotFoundException(UUID id) {
        super("Launchsite not found: " + id);
    }
}
