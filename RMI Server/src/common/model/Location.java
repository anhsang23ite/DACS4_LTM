package common.model;
import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    private int locationId;
    private String address;
    private double latitude, longitude;

    public Location(String address, double lat, double lon) {
        this.address = address; this.latitude = lat; this.longitude = lon;
    }
    // Getter Setter...
    public String getAddress() { return address; }
}