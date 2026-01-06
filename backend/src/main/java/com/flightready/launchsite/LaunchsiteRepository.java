package com.flightready.launchsite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LaunchsiteRepository extends JpaRepository<Launchsite, UUID> {
}
