package common.model;

import java.io.Serializable;

public class Admin implements Serializable {
    private int adminId;
    private String name;
    private String email;
    private String password;

    public Admin(int adminId, String name, String email, String password) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getter và Setter
    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Các phương thức nghiệp vụ (theo UML)
    public boolean login(String user, String pass) {
        return this.email.equals(user) && this.password.equals(pass);
    }

    // Placeholder, các phương thức này sẽ gọi qua RMIService phía Server
    public void viewReportList(String filter) {
        // Để thực thi hợp lý, phải gọi qua Remote Service
    }
    public void viewReportDetail(int reportId) {
        // Để thực thi hợp lý, phải gọi qua Remote Service
    }
    public void updateReportStatus(int reportId, String status) {
        // Để thực thi hợp lý, phải gọi qua Remote Service
    }
    public void sendFeedback(int reportId, String feedback) {
        // Để thực thi hợp lý, phải gọi qua Remote Service
    }
    public void generateStatistics(String criteria) {
        // Để thực thi hợp lý, phải gọi qua Remote Service
    }
}
