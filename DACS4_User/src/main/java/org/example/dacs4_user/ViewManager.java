package org.example.dacs4_user;

import common.model.User;
import impl.RMIClientService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.dacs4_user.controller.*;

import java.io.IOException;
import java.net.URL;

public class ViewManager {
    private final Stage stage;
    private final RMIClientService rmiClient;
    private final String VIEW_PATH = "/org/example/dacs4_user/view/";

    public ViewManager(Stage stage, RMIClientService rmiClient) {
        this.stage = stage;
        this.rmiClient = rmiClient;
    }

    public void showLoginView() {
        showView("login_page.fxml", "Đăng nhập", null);
    }

    public void showRegisterView() {
        showView("register_page.fxml", "Đăng ký Tài khoản", null);
    }

    public void showMainAppView(User user) {
        showView("main_page.fxml", "Quản lý Báo cáo", user);
    }

    public void showNewReportView(User user) {
        showView("new_report_page.fxml", "Báo cáo mới", user);
    }

    public void showReportListView(User user) {
        showView("report_list_page.fxml", "Danh sách Báo cáo", user);
    }

    public void showNotificationView(User user) {
        showView("notification_page.fxml", "Thông báo", user);
    }

    public RMIClientService getRMI() {
        return rmiClient;
    }

    public void showProfileView(User user) {
        showView("profile_page.fxml", "Hồ sơ cá nhân", user);
    }

    public void showEditProfileView(User user) {
        showView("edit_profile_page.fxml", "Chỉnh sửa hồ sơ", user);
    }


    /** Load và hiển thị FXML */
    private void showView(String fxmlFileName, String title, User user) {
        try {
            URL fxmlUrl = getClass().getResource(VIEW_PATH + fxmlFileName);

            if (fxmlUrl == null) {
                System.err.println("Không tìm thấy FXML: " + VIEW_PATH + fxmlFileName);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi Tải Giao Diện");
                alert.setHeaderText("Không tìm thấy tệp FXML");
                alert.setContentText("Vui lòng kiểm tra file: " + VIEW_PATH + fxmlFileName);
                alert.showAndWait();
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(fxmlLoader.load());

            Object controller = fxmlLoader.getController();

            // Gán context vào đúng controller
            if (controller instanceof LoginController) {
                ((LoginController) controller).initContext(this, rmiClient);

            } else if (controller instanceof RegisterController) {
                ((RegisterController) controller).initContext(this, rmiClient);

            } else if (controller instanceof MainAppController) {
                ((MainAppController) controller).initContext(this, user);

            } else if (controller instanceof NewReportController) {
                ((NewReportController) controller).initContext(this, rmiClient, user);

            } else if (controller instanceof ReportListController) {
                ((ReportListController) controller).initContext(this, rmiClient, user);

            } else if (controller instanceof NotificationController) {
                ((NotificationController) controller).initContext(this, rmiClient, user);

            } else if (controller instanceof ProfileController) {
                ((ProfileController) controller).initContext(this, user);

            } else if (controller instanceof EditProfileController) {
                ((EditProfileController) controller).initContext(this, rmiClient, user);
            }

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi Ứng Dụng");
            alert.setHeaderText("Không thể khởi tạo giao diện");
            alert.setContentText("Chi tiết: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
