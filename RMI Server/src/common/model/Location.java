package common.model;

import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    private int locationId;
    private String address;
    private double latitude, longitude;

    public Location() {}

    public Location(String address, double lat, double lon) {
        this.address = address;
        this.latitude = lat;
        this.longitude = lon;
    }

    // --- Getters ---
    public int getLocationId() { return locationId; }
    public String getAddress() { return address; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    // --- Setters ---
    public void setLocationId(int locationId) { this.locationId = locationId; }
    public void setAddress(String address) { this.address = address; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    // THÊM PHƯƠNG THỨC NÀY ĐỂ HẾT LỖI
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}