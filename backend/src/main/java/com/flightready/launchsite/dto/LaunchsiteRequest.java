package com.flightready.launchsite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LaunchsiteRequest {
    @NotBlank
    private String name;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private Double directionStart;

    @NotNull
    private Double directionEnd;

    private String info;

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
