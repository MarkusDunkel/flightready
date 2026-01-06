package com.flightready.launchsite.dto;

import java.util.UUID;

public class LaunchsiteResponse {
    private UUID id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double directionStart;
    private Double directionEnd;
    private String info;

    public LaunchsiteResponse() {
    }

    public LaunchsiteResponse(UUID id, String name, Double latitude, Double longitude,
                              Double directionStart, Double directionEnd, String info) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
