package common.model;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    private int notificationId;
    private int userId;
    private String message;
    private Date createdAt;
    private boolean isRead;

    // Getters và Setters (Bắt buộc)
    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }
    // ... các getters/setters khác ...
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public boolean isRead() { return isRead; } // Chú ý: getter cho boolean là 'isRead'
    public void setRead(boolean read) { isRead = read; }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}