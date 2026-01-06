package com.flightready.launchsite;

import com.flightready.launchsite.dto.LaunchsiteRequest;
import com.flightready.launchsite.dto.LaunchsiteResponse;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LaunchsiteService {
    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory(new PrecisionModel(), 4326);

    private final LaunchsiteRepository repository;

    public LaunchsiteService(LaunchsiteRepository repository) {
        this.repository = repository;
    }

    public List<LaunchsiteResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public LaunchsiteResponse create(LaunchsiteRequest request) {
        Launchsite launchsite = new Launchsite(
                UUID.randomUUID(),
                request.getName(),
                toPoint(request.getLatitude(), request.getLongitude()),
                request.getDirectionStart(),
                request.getDirectionEnd(),
                request.getInfo()
        );
        return toResponse(repository.save(launchsite));
    }

    public LaunchsiteResponse update(UUID id, LaunchsiteRequest request) {
        Launchsite launchsite = repository.findById(id)
                .orElseThrow(() -> new LaunchsiteNotFoundException(id));
        launchsite.setName(request.getName());
        launchsite.setLocation(toPoint(request.getLatitude(), request.getLongitude()));
        launchsite.setDirectionStart(request.getDirectionStart());
        launchsite.setDirectionEnd(request.getDirectionEnd());
        launchsite.setInfo(request.getInfo());
        return toResponse(repository.save(launchsite));
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new LaunchsiteNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private LaunchsiteResponse toResponse(Launchsite launchsite) {
        Point point = launchsite.getLocation();
        Double latitude = null;
        Double longitude = null;
        if (point != null) {
            latitude = point.getY();
            longitude = point.getX();
        }
        return new LaunchsiteResponse(
                launchsite.getId(),
                launchsite.getName(),
                latitude,
                longitude,
                launchsite.getDirectionStart(),
                launchsite.getDirectionEnd(),
                launchsite.getInfo()
        );
    }

    private Point toPoint(Double latitude, Double longitude) {
        Coordinate coordinate = new Coordinate(longitude, latitude);
        Point point = GEOMETRY_FACTORY.createPoint(coordinate);
        point.setSRID(4326);
        return point;
    }
}
