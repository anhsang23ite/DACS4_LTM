package common.model;
import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    private int locationId;
    private String address;
    private double latitude, longitude;

    // Constructor mặc định (cần thiết cho RMI/JDBC nếu bạn khởi tạo đối tượng rỗng)
    public Location() {}

    public Location(String address, double lat, double lon) {
        this.address = address; this.latitude = lat; this.longitude = lon;
    }

    // --- Getters ---
    public int getLocationId() { return locationId; }
    public String getAddress() { return address; }

    // ⭐ PHƯƠNG THỨC BỊ THIẾU GÂY LỖI
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    // --- Setters ---
    public void setLocationId(int locationId) { this.locationId = locationId; }
    public void setAddress(String address) { this.address = address; }

    // ⭐ PHƯƠNG THỨC SETTER (nên có)
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}