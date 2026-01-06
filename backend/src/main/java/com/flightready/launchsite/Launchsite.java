package com.flightready.launchsite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
@Table(name = "launchsites")
public class Launchsite {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @JdbcTypeCode(SqlTypes.GEOGRAPHY)
    @Column(nullable = false, columnDefinition = "geography(Point,4326)")
    private Point location;

    @Column(name = "direction_start", nullable = false)
    private Double directionStart;

    @Column(name = "direction_end", nullable = false)
    private Double directionEnd;

    private String info;

    protected Launchsite() {
    }

    public Launchsite(UUID id, String name, Point location, Double directionStart, Double directionEnd, String info) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.directionStart = directionStart;
        this.directionEnd = directionEnd;
        this.info = info;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Double getDirectionStart() {
        return directionStart;
    }

    public void setDirectionStart(Double directionStart) {
        this.directionStart = directionStart;
    }

    public Double getDirectionEnd() {
        return directionEnd;
    }

    public void setDirectionEnd(Double directionEnd) {
        this.directionEnd = directionEnd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
