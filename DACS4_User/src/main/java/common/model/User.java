package common.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userId;
    private String name, email, password, phoneNumber;

    public User() {}

    // Constructor 4 tham số (Dùng cho đăng ký mới - không cần ID)
    public User(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // --- CẦN THÊM: Constructor 5 tham số (Dùng cho Admin cập nhật thông tin) ---
    public User(int userId, String name, String email, String password, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Getter và Setter cho UserId
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    // Getter và Setter cho Name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Getter và Setter cho Email
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Getter và Setter cho Password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Getter và Setter cho PhoneNumber
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}