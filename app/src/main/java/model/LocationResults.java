package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gohv on 03.03.17.
 */
public class LocationResults {
    private double latitude;
    private double longitude;
    private String formatted_address;
    private String street_number;
    public List<LocationResults> results;

    public LocationResults() {
        results = new ArrayList<>();
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

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
