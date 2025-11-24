package common.model;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    private int notificationId;
    private int userId;
    private String message;
    private Date createdAt;
    private boolean isRead;

    public Notification(int notificationId, int userId, String message, Date createdAt, boolean isRead) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    // Getter và Setter
    public int getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        isRead = read;
    }

    // Phương thức nghiệp vụ
    public void markAsRead() {
        this.isRead = true;
    }
}
