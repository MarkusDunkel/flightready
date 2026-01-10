package com.flightready.launchsite;

import com.flightready.launchsite.dto.LaunchsiteRequest;
import com.flightready.launchsite.dto.LaunchsiteResponse;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LaunchsiteService {

    private static final int SRID_WGS84 = 4326;
    private static final GeometryFactory GEOMETRY_FACTORY =
            new GeometryFactory(new PrecisionModel(), SRID_WGS84);

    private final LaunchsiteRepository repository;

    public LaunchsiteService(LaunchsiteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<LaunchsiteResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public LaunchsiteResponse create(LaunchsiteRequest request) {
        Launchsite launchsite = new Launchsite(
                UUID.randomUUID(),
                request.name(),
                toPoint(request.latitude(), request.longitude()),
                request.directionStart(),
                request.directionEnd(),
                request.asl(),
                request.info()
        );

        Launchsite saved = repository.save(launchsite);
        return toResponse(saved);
    }

    @Transactional
    public LaunchsiteResponse update(UUID id, LaunchsiteRequest request) {
        Launchsite launchsite = repository.findById(id)
                .orElseThrow(() -> new LaunchsiteNotFoundException(id));

        launchsite.updateDetails(
                request.name(),
                toPoint(request.latitude(), request.longitude()),
                request.directionStart(),
                request.directionEnd(),
                request.asl(),
                request.info()
        );

        return toResponse(launchsite);
    }

    @Transactional
    public void delete(UUID id) {
        Launchsite launchsite = repository.findById(id)
                .orElseThrow(() -> new LaunchsiteNotFoundException(id));
        repository.delete(launchsite);
    }

    private LaunchsiteResponse toResponse(Launchsite launchsite) {
        Point point = launchsite.getLocation();
        Double latitude = point == null ? null : point.getY();
        Double longitude = point == null ? null : point.getX();

        return new LaunchsiteResponse(
                launchsite.getId(),
                launchsite.getName(),
                latitude,
                longitude,
                launchsite.getDirectionStart(),
                launchsite.getDirectionEnd(),
                launchsite.getAsl(),
                launchsite.getInfo()
        );
    }

    private Point toPoint(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude and longitude must not be null");
        }
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude out of range: " + latitude);
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude out of range: " + longitude);
        }

        Point point = GEOMETRY_FACTORY.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID_WGS84);
        return point;
    }
}
