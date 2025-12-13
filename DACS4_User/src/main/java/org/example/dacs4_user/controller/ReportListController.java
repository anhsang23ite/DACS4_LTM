package org.example.dacs4_user.controller;

import common.model.Report;
import common.model.User;
import impl.RMIClientService;
import javafx.beans.property.SimpleIntegerProperty;
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

public class ReportListController {

    // 1. Thay thế ListView bằng TableView
    @FXML
    private TableView<Report> reportTableView;

    // 2. Khai báo các cột FXML IDs
    @FXML private TableColumn<Report, Integer> reportIdCol;
    @FXML private TableColumn<Report, String> titleCol;
    @FXML private TableColumn<Report, String> statusCol;
    @FXML private TableColumn<Report, String> createdAtCol;
    // Hoặc String nếu bạn muốn định dạng Date trong Model

    private ViewManager viewManager;
    private RMIClientService rmiClient;
    private User currentUser;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public void initContext(ViewManager viewManager, RMIClientService rmiClient, User user) {
        this.viewManager = viewManager;
        this.rmiClient = rmiClient;
        this.currentUser = user;

        // *** CẤU HÌNH CỘT CHO TABLEVIEW ***
        configureTableColumns();

        loadReports();
    }

    private void configureTableColumns() {
        // Cột 1: ID (Lấy giá trị int reportId)
        reportIdCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getReportId()).asObject());

        // Cột 2: Tiêu đề (Lấy giá trị String title)
        titleCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        // Cột 3: Trạng thái (Lấy giá trị String status)
        statusCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));

        // Cột 4: Ngày gửi (Lấy giá trị Date createdAt và định dạng thành String)
        createdAtCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCreatedAt() != null) {
                return new SimpleStringProperty(dateFormat.format(cellData.getValue().getCreatedAt()));
            } else {
                return new SimpleStringProperty(""); // Hoặc "N/A"
            }
        });


        // (Tùy chọn) Thêm CellFactory để định dạng màu cho cột Trạng thái
        statusCol.setCellFactory(column -> new TableCell<Report, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("RESOLVED".equalsIgnoreCase(item)) {
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else if ("PENDING".equalsIgnoreCase(item)) {
                        setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });
    }


    private void loadReports() {
        try {
            // SỬA: Thay thế getMyReports bằng getReportList
            List<Report> reports = rmiClient.getRemoteService().getReportList(currentUser.getUserId());

            reportTableView.getItems().clear();

            if (reports != null && !reports.isEmpty()) {
                // Thêm dữ liệu vào TableView
                reportTableView.getItems().addAll(reports);
            } else {
                showAlert("Thông báo", "Bạn chưa gửi bất kỳ báo cáo nào.", Alert.AlertType.INFORMATION);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            showAlert("Lỗi kết nối", "Không thể tải báo cáo: " + e.getMessage(), Alert.AlertType.ERROR);
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

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}