package org.example.dacs4_user.controller;

import common.model.User;
import impl.RMIClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.example.dacs4_user.ViewManager;

import java.rmi.RemoteException;

public class LoginController {

    @FXML
    private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private ViewManager viewManager;
    private RMIClientService rmiClient;

    // Phương thức được ViewManager gọi để thiết lập ngữ cảnh
    public void initContext(ViewManager viewManager, RMIClientService rmiClient) {
        this.viewManager = viewManager;
        this.rmiClient = rmiClient;
    }

    @FXML
    private void login() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            setStatus("Vui lòng nhập đầy đủ Email và Mật khẩu.", Color.RED);
            return;
        }

        if (rmiClient.getRemoteService() == null) {
            setStatus("Không thể kết nối Server RMI. Vui lòng kiểm tra lại.", Color.RED);
            return;
        }

        setStatus("Đang đăng nhập...", Color.BLUE);

        try {
            // Gọi phương thức login() qua RMI
            User authenticatedUser = rmiClient.getRemoteService().login(email, password);

            if (authenticatedUser != null) {
                setStatus("Đăng nhập thành công!", Color.GREEN);
                // Chuyển sang giao diện chính
                viewManager.showMainAppView(authenticatedUser);
            } else {
                setStatus("Email hoặc Mật khẩu không đúng. Vui lòng thử lại.", Color.RED);
            }

        } catch (RemoteException e) {
            setStatus("Lỗi mạng hoặc Server: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        }
    }

    @FXML
    private void showRegisterView() {
        viewManager.showRegisterView();
    }

    private void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setTextFill(color);
    }
}