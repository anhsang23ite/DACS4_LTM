module org.example.dacs4_user {
    // Các module JavaFX mà ứng dụng Client của bạn cần
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    // Module RMI
    requires java.rmi;

    opens org.example.dacs4_user to javafx.fxml;
    exports org.example.dacs4_user;

    // ⭐ KHẮC PHỤC LỖI: Cần mở package chứa Controller cho JavaFX truy cập
    opens org.example.dacs4_user.controller to javafx.fxml;
    exports org.example.dacs4_user.controller; // Nên exports nếu muốn các module khác dùng Controller
}