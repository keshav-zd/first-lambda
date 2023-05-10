package com.exam1;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Objects;

public class Location {
    String type;
    String coordinates;

    public Location(JsonObject locationdata) {
        type = locationdata.get("type").getAsString();
        JsonArray coordinatesArray = locationdata.get("coordinates").getAsJsonArray();
        coordinates = "[" + coordinatesArray.get(0).getAsString() + ", " + coordinatesArray.get(1).getAsString() + "]";
    }

    public String getType() {
        return type;
    }

    public String getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(type, location.type) && Objects.equals(coordinates, location.coordinates);
    }

    @Override
    public String toString() {
        return "Location{" +
                "type='" + type + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, coordinates);
    }
}



