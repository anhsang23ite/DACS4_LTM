package common.model;

import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    private int locationId;
    private String address;
    private double latitude, longitude;

    // 1. Constructor mặc định (đã có)
    public Location() {}

    // 2. Constructor 3 tham số (đã có)
    public Location(String address, double lat, double lon) {
        this.address = address;
        this.latitude = lat;
        this.longitude = lon;
    }

    // ⭐ 3. CONSTRUCTOR MỘT THAM SỐ ĐÃ BỊ THIẾU (KHẮC PHỤC LỖI)
    public Location(String address) {
        this.address = address;
        // Gán giá trị mặc định cho Lat/Lon, vì chúng không được cung cấp
        this.latitude = 0.0;
        this.longitude = 0.0;
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
    public void setLongitude(double longitude) { this.longitude = longitude; }
}