package common.model;

import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    private int locationId;
    private String address;
    private double latitude;
    private double longitude;

    public Location() {
    }

    public Location(int locationId, String address, double latitude, double longitude) {
        this.locationId = locationId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String address) {
        this.address = address;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public double[] getCoordinates() {
        return new double[]{latitude, longitude};
    }
}
