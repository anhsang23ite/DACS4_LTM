package common.model;
import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
    private int reportId, userId;
    private String title, description, image, status, feedback;
    private Location location;
    private Date createdAt, updatedAt;
    public Report(int reportId, int userId, String title, String description, String image,
                  Location location, String status, String feedback, Date createdAt, Date updatedAt) {
        this.reportId = reportId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.location = location;
        this.status = status;
        this.feedback = feedback;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public int getReportId() { return reportId; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public Location getLocation() { return location; }
    public String getStatus() { return status; }
    public String getFeedback() { return feedback; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void updateStatus(String status) { this.status = status; }
    public void addFeedback(String feedback) { this.feedback = feedback; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}