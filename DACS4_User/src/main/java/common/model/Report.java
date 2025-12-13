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

    // --- Getters and Setters ---

    // reportId
    public int getReportId() { return reportId; }
    public void setReportId(int reportId) { this.reportId = reportId; }

    // userId
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    // title
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    // description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // image
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    // status
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // feedback
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    // location
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    // createdAt
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}