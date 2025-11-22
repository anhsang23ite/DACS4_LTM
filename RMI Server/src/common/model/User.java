package common.model;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Quan trọng để đồng bộ version
    private int userId;
    private String name, email, password, phoneNumber;

    public User() {}
    public User(String name, String email, String password, String phoneNumber) {
        this.name = name; this.email = email; this.password = password; this.phoneNumber = phoneNumber;
    }
    // Getter & Setter (Bạn tự generate nhé để code ngắn gọn)
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setName(String name) {
    }
    // ...
}