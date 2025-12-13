package org.example.dacs4_user.controller;

import common.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.dacs4_user.ViewManager;

public class MainAppController {

    @FXML
    private Label totalReportsLabel;

    @FXML
    private Label pendingReportsLabel;

    @FXML
    private Label welcomeLabel;

    private ViewManager viewManager;
    private User currentUser;

    public void initContext(ViewManager viewManager, User user) {
        System.out.println(">>> initContext MainAppController chạy");
        System.out.println("User: " + user);

        this.viewManager = viewManager;
        this.currentUser = user;

        welcomeLabel.setText("Xin chào, " + user.getName() + "!");

        int total = viewManager.getRMI().countReports(user.getUserId());
        int pending = viewManager.getRMI().countPendingReports(user.getUserId());

        totalReportsLabel.setText(String.valueOf(total));
        pendingReportsLabel.setText(String.valueOf(pending));

        System.out.println("Total = " + total + ", pending = " + pending);
    }


    @FXML
    private void logout() {
        this.currentUser = null;
        viewManager.showLoginView();
    }

    // Nút Báo cáo mới
    @FXML
    private void showNewReportView() {
        if (currentUser != null) {
            viewManager.showNewReportView(currentUser);
        }
    }

    // Nút Danh sách báo cáo
    @FXML
    private void showReportListView() {
        if (currentUser != null) {
            viewManager.showReportListView(currentUser);
        }
    }

    // 👉 Nút Hồ sơ — đã sửa để chuyển sang trang profile_page.fxml
    @FXML
    private void showProfileView() {
        if (currentUser != null) {
            System.out.println(">> Chuyển sang trang hồ sơ");
            viewManager.showProfileView(currentUser);
        }
    }

    // Nút Thông báo
    @FXML
    private void showNotificationView() {
        if (currentUser != null) {
            viewManager.showNotificationView(currentUser);
        }
    }
}
