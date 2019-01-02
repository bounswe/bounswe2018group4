package com.memorist.memorist_android.model;

import com.google.gson.annotations.Expose;

public class LocationType {

    @Expose
    private int location_type;

    @Expose
    private Location[] location_list;

    public int getLocation_type() {
        return location_type;
    }

    public Location[] getLocation() {
        return location_list;
    }

    public void setLocation(Location[] location) {
        this.location_list = location;
    }

    public void setLocation_type(int location_type) {
        this.location_type = location_type;
    }
}
