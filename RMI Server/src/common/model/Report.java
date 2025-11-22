package common.model;
import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    private int reportId;
    private int userId;
    private String title, description, image, status, feedback;
    private Location location; // Quan hệ Has-A
    private Date createdAt;

    // Getter & Setter
    public void setLocation(Location location) { this.location = location; }
    public Location getLocation() { return location; }
    // ... (Generate full getters/setters)
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void setDescription(String description) { this.description = description; }
    public String getDescription() { return description; }
}