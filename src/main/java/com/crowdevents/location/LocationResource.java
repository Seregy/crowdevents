package com.crowdevents.location;

import com.crowdevents.core.web.Views;
import com.fasterxml.jackson.annotation.JsonView;

@JsonView(Views.Minimal.class)
public class LocationResource {
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
