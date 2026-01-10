package com.flightready.weather;

import com.flightready.launchsite.Launchsite;
import com.flightready.weather.dto.meteoblue.MeteoblueWeatherResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "weather",
        indexes = @Index(
                name = "idx_weather_location_fetched",
                columnList = "launchsite_id, fetched_at DESC"
        )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@ToString(exclude = "launchsite")
public class Weather {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private String provider;

    @Column(name = "schema_version", nullable = false, updatable = false)
    private String schemaVersion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "launchsite_id", nullable = false)
    private Launchsite launchsite;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private MeteoblueWeatherResponse payload;

    @CreationTimestamp
    @Column(name = "fetched_at", nullable = false, updatable = false)
    private OffsetDateTime fetchedAt;

    private Weather(String provider, String schemaVersion, Launchsite launchsite, MeteoblueWeatherResponse payload) {
        this.provider = provider;
        this.schemaVersion = (schemaVersion == null ? "v1" : schemaVersion);
        this.launchsite = launchsite;
        this.payload = payload;
    }

    public static Weather meteoblue(Launchsite site,MeteoblueWeatherResponse payload) {
        return new Weather("meteoblue", "v1", site, payload);
    }

    public static Weather of(String provider, Launchsite site,MeteoblueWeatherResponse payload) {
        return new Weather(provider, "v1", site, payload);
    }
}
