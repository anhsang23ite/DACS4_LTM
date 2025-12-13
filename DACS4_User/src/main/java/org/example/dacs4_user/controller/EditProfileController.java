package org.example.dacs4_user.controller;

import common.model.User;
import impl.RMIClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.dacs4_user.ViewManager;

public class EditProfileController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    private ViewManager viewManager;
    private RMIClientService rmi;
    private User currentUser;

    public void initContext(ViewManager vm, RMIClientService rmi, User user) {
        this.viewManager = vm;
        this.rmi = rmi;
        this.currentUser = user;

        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhoneNumber());
    }

    @FXML
    private void saveProfile() {
        try {
            currentUser.setName(nameField.getText());
            currentUser.setEmail(emailField.getText());
            currentUser.setPhoneNumber(phoneField.getText());

            boolean ok = rmi.getRemoteService().updateUserProfile(currentUser);

            if (ok) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cập nhật hồ sơ thành công!");
                alert.showAndWait();

                viewManager.showProfileView(currentUser);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cập nhật thất bại!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() {
        viewManager.showProfileView(currentUser);
    }
}
