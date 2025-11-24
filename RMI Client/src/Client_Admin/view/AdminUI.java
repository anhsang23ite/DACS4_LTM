package Client_Admin.view;

import Client_Admin.controller.AdminController;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import common.remote.RMIService;
import common.model.Report;
import java.rmi.Naming;

public class AdminUI extends Application {
    private AdminController controller;
    private TableView<Report> table;
    private ObservableList<Report> reportList;

    @Override
    public void start(Stage primaryStage) {
        try {
            RMIService service = (RMIService) Naming.lookup("rmi://localhost/RMIService");
            controller = new AdminController(service);
        } catch (Exception e) {
            showError("Không kết nối được đến RMI Server!\n" + e.getMessage());
            return;
        }

        primaryStage.setTitle("Admin - Quản lý báo cáo");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        reportList = FXCollections.observableArrayList();
        table = new TableView<>(reportList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Report, String> colId     = new TableColumn<>("ID");
        TableColumn<Report, String> colTitle  = new TableColumn<>("Tiêu đề");
        TableColumn<Report, String> colStatus = new TableColumn<>("Trạng thái");
        TableColumn<Report, String> colUser   = new TableColumn<>("UserID");
        TableColumn<Report, String> colDesc   = new TableColumn<>("Mô tả");
        TableColumn<Report, String> colFeedback = new TableColumn<>("Phản hồi");

        colId.setCellValueFactory( r -> new javafx.beans.property.SimpleStringProperty("" + r.getValue().getReportId()) );
        colTitle.setCellValueFactory( r -> new javafx.beans.property.SimpleStringProperty(r.getValue().getTitle()) );
        colStatus.setCellValueFactory( r -> new javafx.beans.property.SimpleStringProperty(r.getValue().getStatus()) );
        colUser.setCellValueFactory( r -> new javafx.beans.property.SimpleStringProperty("" + r.getValue().getUserId()) );
        colDesc.setCellValueFactory( r -> new javafx.beans.property.SimpleStringProperty(r.getValue().getDescription()) );
        colFeedback.setCellValueFactory( r -> new javafx.beans.property.SimpleStringProperty(r.getValue().getFeedback()) );

        table.getColumns().addAll(colId, colTitle, colStatus, colUser, colDesc, colFeedback);

        TextField txtStatus = new TextField();
        txtStatus.setPromptText("Trạng thái mới");
        Button btnUpdateStatus = new Button("Cập nhật trạng thái");

        btnUpdateStatus.setOnAction(e -> {
            Report selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Hãy chọn báo cáo!");
                return;
            }
            try {
                boolean ok = controller.updateReportStatus(selected.getReportId(), txtStatus.getText());
                if (ok) {
                    selected.updateStatus(txtStatus.getText());
                    table.refresh();
                    showInfo("Đã cập nhật trạng thái!");
                } else showError("Cập nhật thất bại!");
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        TextField txtFeedback = new TextField();
        txtFeedback.setPromptText("Phản hồi nội dung");
        Button btnSendFeedback = new Button("Gửi phản hồi");

        btnSendFeedback.setOnAction(e -> {
            Report selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Hãy chọn báo cáo!");
                return;
            }
            try {
                boolean ok = controller.sendFeedback(selected.getReportId(), txtFeedback.getText());
                if (ok) {
                    selected.addFeedback(txtFeedback.getText());
                    table.refresh();
                    showInfo("Đã gửi phản hồi!");
                } else showError("Gửi thất bại!");
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        Button btnRefresh = new Button("Tải danh sách");
        btnRefresh.setOnAction(e -> loadReports());

        HBox bottom = new HBox(10,
                new Label("Trạng thái:"), txtStatus, btnUpdateStatus,
                new Label("Phản hồi:"), txtFeedback, btnSendFeedback, btnRefresh);
        bottom.setPadding(new Insets(10));
        root.setCenter(table);
        root.setBottom(bottom);

        loadReports();
        primaryStage.setScene(new Scene(root, 900, 400));
        primaryStage.show();
    }

    private void loadReports() {
        try {
            reportList.setAll(controller.getAllReports());
        } catch (Exception e) {
            showError("Không tải được báo cáo!\n" + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK); a.showAndWait();
    }
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK); a.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}