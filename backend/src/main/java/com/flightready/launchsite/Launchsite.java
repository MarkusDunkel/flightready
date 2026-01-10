package com.flightready.launchsite;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@Entity
@Table(name = "launchsite")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)   // required by JPA
@AllArgsConstructor
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
    private double directionStart;

    @Column(name = "direction_end", nullable = false)
    private double directionEnd;

    @Column(nullable = false)
    private int asl;

    private String info;

    public void updateDetails(
            String name,
            Point location,
            Double directionStart,
            Double directionEnd,
            Integer asl,
            String info
    ) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if (location == null || directionStart == null || directionEnd == null || asl == null)
            throw new IllegalArgumentException("location must not be null");

        this.name = name;
        this.location = location;
        this.directionStart = directionStart;
        this.directionEnd = directionEnd;
        this.asl = asl;
        this.info = info;
    }
}
