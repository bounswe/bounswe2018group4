package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Location implements Serializable {

    @Expose
    String location_name;

    @Expose
    double location_coordinate_latitude;

    @Expose
    double location_coordinate_longitude;

    public Location(String location_name, double location_coordinate_latitude, double location_coordinate_longtitude) {
        this.location_name = location_name;
        this.location_coordinate_latitude = location_coordinate_latitude;
        this.location_coordinate_longitude = location_coordinate_longtitude;
    }

    public double getLocation_coordinate_latitude() {
        return location_coordinate_latitude;
    }

    public double getLocation_coordinate_longtitude() {
        return location_coordinate_longitude;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_coordinate_latitude(double location_coordinate_latitude) {
        this.location_coordinate_latitude = location_coordinate_latitude;
    }

    public void setLocation_coordinate_longtitude(double location_coordinate_longtitude) {
        this.location_coordinate_longitude = location_coordinate_longtitude;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }
}
