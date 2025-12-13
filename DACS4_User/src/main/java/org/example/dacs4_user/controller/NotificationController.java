package org.example.dacs4_user.controller;

import common.model.Notification;
import common.model.User;
import impl.RMIClientService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.dacs4_user.ViewManager;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class NotificationController {

    @FXML private TableView<Notification> notificationTableView;
    @FXML private TableColumn<Notification, String> messageCol;
    @FXML private TableColumn<Notification, String> createdAtCol;
    @FXML private TableColumn<Notification, Boolean> isReadCol;

    private ViewManager viewManager;
    private RMIClientService rmiClient;
    private User currentUser;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public void initContext(ViewManager viewManager, RMIClientService rmiClient, User user) {
        this.viewManager = viewManager;
        this.rmiClient = rmiClient;
        this.currentUser = user;

        configureTableColumns();
        loadNotifications();
    }

    private void configureTableColumns() {
        // Cột Nội dung
        messageCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMessage()));

        // Cột Thời gian (Định dạng từ Date sang String)
        createdAtCol.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getCreatedAt();
            return new SimpleStringProperty(date != null ? dateFormat.format(date) : "");
        });

        // Cột Trạng thái (isRead - Hiển thị Đã đọc/Chưa đọc)
        isReadCol.setCellValueFactory(cellData ->
                new SimpleBooleanProperty(cellData.getValue().isRead()).asObject());

        // Thêm CellFactory để định dạng màu/văn bản cho cột Trạng thái
        isReadCol.setCellFactory(column -> new TableCell<Notification, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item ? "Đã đọc" : "CHƯA ĐỌC");
                    setStyle(item ? "-fx-text-fill: #4CAF50;" : "-fx-text-fill: #D32F2F; -fx-font-weight: bold;");
                }
            }
        });

        notificationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && !newSelection.isRead()) {
                // Đánh dấu là đã đọc khi người dùng nhấp vào
                try {
                    // CẦN TRIỂN KHAI updateNotificationStatus trong RMIService
                    // boolean success = rmiClient.getRemoteService().updateNotificationStatus(newSelection.getNotificationId(), true);
                    newSelection.setRead(true); // Cập nhật tạm thời trên UI
                    notificationTableView.refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadNotifications() {
        try {
            // CẦN TRIỂN KHAI getNotificationsByUserId trong RMIService
            // List<Notification> notifications = rmiClient.getRemoteService().getNotificationsByUserId(currentUser.getUserId());

            // Dữ liệu giả (Placeholder)
            List<Notification> notifications = List.of(
                    createDummyNotification(1, "Báo cáo #101 đã được duyệt.", false),
                    createDummyNotification(2, "Phản hồi mới từ Admin.", true)
            );

            notificationTableView.getItems().clear();
            if (notifications != null && !notifications.isEmpty()) {
                notificationTableView.getItems().addAll(notifications);
            } else {
                showAlert("Thông báo", "Không có thông báo mới nào.", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi kết nối", "Không thể tải thông báo: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void markAllAsRead() {
        // CẦN TRIỂN KHAI markAllNotificationsAsRead trong RMIService
        try {
            // boolean success = rmiClient.getRemoteService().markAllNotificationsAsRead(currentUser.getUserId());
            // Cập nhật UI
            for (Notification n : notificationTableView.getItems()) {
                n.setRead(true);
            }
            notificationTableView.refresh();
            showAlert("Thành công", "Đã đánh dấu tất cả là đã đọc.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể cập nhật trạng thái.", Alert.AlertType.ERROR);
        }
    }
    // Thêm vào NewReportController, ReportListController, NotificationController
    @FXML
    private void showMainAppView() {
        if (currentUser != null) {
            // Gọi ViewManager để quay lại trang chính
            viewManager.showMainAppView(currentUser);
        }
    }

    // Hàm tạo dữ liệu giả để kiểm tra giao diện
    private Notification createDummyNotification(int id, String message, boolean read) {
        Notification n = new Notification();
        n.setNotificationId(id);
        n.setUserId(currentUser.getUserId());
        n.setMessage(message);
        n.setCreatedAt(new Date());
        n.setRead(read);
        return n;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}