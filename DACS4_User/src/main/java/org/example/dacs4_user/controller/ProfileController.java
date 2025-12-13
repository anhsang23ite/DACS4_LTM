package org.example.dacs4_user.controller;

import common.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.dacs4_user.ViewManager;

public class ProfileController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    private ViewManager viewManager;
    private User currentUser;

    public void initContext(ViewManager vm, User user) {
        this.viewManager = vm;
        this.currentUser = user;

        nameLabel.setText(user.getName());
        emailLabel.setText(user.getEmail());
        phoneLabel.setText(user.getPhoneNumber());
    }

    @FXML
    private void editProfile() {
        viewManager.showEditProfileView(currentUser);
    }

    @FXML
    private void goBack() {
        viewManager.showMainAppView(currentUser);
    }
}
