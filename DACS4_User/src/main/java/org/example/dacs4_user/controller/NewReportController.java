package org.example.dacs4_user.controller;

import common.model.Location;
import common.model.Report;
import common.model.User;
import impl.RMIClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.example.dacs4_user.ViewManager;

import java.io.File;
import java.rmi.RemoteException;

public class NewReportController {

    @FXML
    private TextField titleField;
    @FXML private TextArea descriptionArea;

    // Đã đổi tên và cập nhật fx:id
    @FXML private TextField imagePathField; // Hiển thị đường dẫn file ảnh
    @FXML private TextField locationField; // Vị trí/Tọa độ

    private ViewManager viewManager;
    private RMIClientService rmiClient;
    private User currentUser;

    // Biến để lưu trữ đối tượng File đã chọn
    private File selectedImageFile;

    public void initContext(ViewManager viewManager, RMIClientService rmiClient, User user) {
        this.viewManager = viewManager;
        this.rmiClient = rmiClient;
        this.currentUser = user;
    }

    /**
     * Phương thức mở hộp thoại để chọn file ảnh
     */
    @FXML
    private void chooseImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn File Ảnh");
        // Thiết lập bộ lọc cho các loại ảnh phổ biến
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Lấy Stage hiện tại từ một thành phần bất kỳ (ví dụ: titleField)
        File file = fileChooser.showOpenDialog(titleField.getScene().getWindow());

        if (file != null) {
            selectedImageFile = file;
            imagePathField.setText(file.getAbsolutePath());
        }
    }

    /**
     * Phương thức này sẽ được phát triển sau để mở giao diện Map
     */
    @FXML
    private void openMapForLocation() {
        // Hiện tại chỉ là placeholder. Cần triển khai tích hợp Map (ví dụ: JavaFX-Webview + Google Maps API)
        showAlert("Thông báo", "Chức năng chọn vị trí từ Bản đồ đang được phát triển.", Alert.AlertType.INFORMATION);
    }

    // Thêm vào NewReportController, ReportListController, NotificationController
    @FXML
    private void showMainAppView() {
        if (currentUser != null) {
            // Gọi ViewManager để quay lại trang chính
            viewManager.showMainAppView(currentUser);
        }
    }


    @FXML
    private void submitReport() {
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        String locationData = locationField.getText().trim();

        // Kiểm tra validation
        if(title.isEmpty() || description.isEmpty() || locationData.isEmpty() || selectedImageFile == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin và chọn ảnh.", Alert.AlertType.WARNING);
            return;
        }

        Report report = new Report();
        report.setUserId(currentUser.getUserId());
        report.setTitle(title);
        report.setDescription(description);

        // Dùng đường dẫn file ảnh (hoặc URL nếu server có cơ chế lưu trữ)
        // Trong trường hợp này, ta sử dụng đường dẫn tuyệt đối của file đã chọn
        report.setImage(selectedImageFile.getAbsolutePath());

        // Thiết lập Location
        report.setLocation(new Location(locationData));

        try {
            // ... (Phần gọi RMI giữ nguyên)
            boolean success = rmiClient.getRemoteService().sendReport(report);

            if(success) {
                showAlert("Thành công", "Báo cáo đã được gửi.", Alert.AlertType.INFORMATION);

                // Reset UI và biến
                titleField.clear();
                descriptionArea.clear();
                imagePathField.clear();
                locationField.clear();
                selectedImageFile = null;

                viewManager.showMainAppView(currentUser);
            } else {
                showAlert("Thất bại", "Không thể gửi báo cáo. Vui lòng thử lại.", Alert.AlertType.ERROR);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            showAlert("Lỗi kết nối", "Không thể kết nối đến máy chủ RMI: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}