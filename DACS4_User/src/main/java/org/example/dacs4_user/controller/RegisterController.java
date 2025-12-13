package org.example.dacs4_user.controller;

import common.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.example.dacs4_user.ViewManager;
import impl.RMIClientService;

import java.rmi.RemoteException;

public class RegisterController {

    @FXML
    private TextField nameField;
    @FXML private TextField regEmailField;
    @FXML private PasswordField regPasswordField;
    @FXML private TextField phoneField;
    @FXML private Label statusLabel;

    private ViewManager viewManager;
    private RMIClientService rmiClient;

    // Thiết lập context
    public void initContext(ViewManager viewManager, RMIClientService rmiClient) {
        this.viewManager = viewManager;
        this.rmiClient = rmiClient;
    }

    @FXML
    private void register() {

        String name = nameField.getText().trim();
        String email = regEmailField.getText().trim();
        String password = regPasswordField.getText().trim();
        String phone = phoneField.getText().trim();

        // Kiểm tra dữ liệu rỗng
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            setStatus("Vui lòng nhập đầy đủ thông tin đăng ký.", Color.RED);
            return;
        }

        // Kiểm tra kết nối RMI
        if (rmiClient.getRemoteService() == null) {
            setStatus("Không thể kết nối Server RMI. Vui lòng kiểm tra lại.", Color.RED);
            return;
        }

        setStatus("Đang xử lý đăng ký...", Color.BLUE);

        User newUser = new User(name, email, password, phone);

        try {
            boolean success = rmiClient.getRemoteService().register(newUser);

            if (success) {
                // Hiển thị Alert thành công
                showAlert("Đăng ký thành công!", "Bạn đã đăng ký thành công. Vui lòng đăng nhập.", Alert.AlertType.INFORMATION);
                viewManager.showLoginView();
            } else {
                // Hiển thị Alert lỗi
                showAlert("Đăng ký thất bại", "Email đã tồn tại hoặc server từ chối yêu cầu.", Alert.AlertType.ERROR);
            }

        } catch (RemoteException e) {
            // Hiển thị lỗi kết nối
            showAlert("Lỗi kết nối server", "Đăng ký thất bại:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void showLoginView() {
        viewManager.showLoginView();
    }

    // Cập nhật statusLabel
    private void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setTextFill(color);
    }

    // Hiển thị Alert popup
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

