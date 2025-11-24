package common.model;
import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    public User(int userId, String name, String email, String password, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
}