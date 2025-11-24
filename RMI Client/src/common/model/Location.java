package common.model;
import java.io.Serializable;

public class Location implements Serializable {
    private int locationId;
    private String address;
    private double latitude, longitude;
    public Location(int locationId, String address, double latitude, double longitude) {
        this.locationId = locationId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public int getLocationId() { return locationId; }
    public String getAddress() { return address; }
    public double[] getCoordinates() { return new double[]{latitude, longitude}; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}