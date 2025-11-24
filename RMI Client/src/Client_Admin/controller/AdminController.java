package Client_Admin.controller;

import common.remote.RMIService;
import common.model.Report;

import java.util.List;

public class AdminController {
    private RMIService service;

    public AdminController(RMIService service) {
        this.service = service;
    }

    public List<Report> getAllReports() throws Exception {
        // Nếu truyền userId=0 => trả về toàn bộ báo cáo
        return service.getReportList(0);
    }

    public boolean updateReportStatus(int reportId, String status) throws Exception {
        return service.updateReportStatus(reportId, status);
    }

    public boolean sendFeedback(int reportId, String feedback) throws Exception {
        return service.sendFeedback(reportId, feedback);
    }

    public Report getReportDetail(int reportId) throws Exception {
        return service.getReportDetail(reportId);
    }
}