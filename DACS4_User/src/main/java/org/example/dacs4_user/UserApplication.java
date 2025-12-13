package org.example.dacs4_user;

import impl.RMIClientService;
import javafx.application.Application;
import javafx.stage.Stage;

public class UserApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ...

        // 1. Khởi tạo RMI Client
        RMIClientService rmiClient = new RMIClientService();

        // 2. Khởi tạo ViewManager (tham chiếu Stage và RMI Client)
        ViewManager viewManager = new ViewManager(primaryStage, rmiClient);

        // 3. Hiển thị Trang Đăng nhập lần đầu tiên <--- Đảm bảo đây là màn hình đầu tiên
        viewManager.showLoginView();
    }
    // ...
}
