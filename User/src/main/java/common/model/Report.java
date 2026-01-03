package common.model;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    private int reportId;
    private int userId;
    private String title;
    private String description;
    private String image;
    private String status;
    private String feedback;
    private Location location;
    private Date createdAt;
    private Date updatedAt;

    public Report() {
    }

    public Report(int reportId, int userId, String title, String description,
                  String image, Location location, String status,
                  String feedback, Date createdAt, Date updatedAt) {
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

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void addFeedback(String feedback) {
        this.feedback = feedback;
    }
}
