package impl;

import common.remote.RMIService;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIClientService {

    // Trường để lưu trữ đối tượng Remote Service đã được lookup
    private RMIService remoteService;

    // Địa chỉ và cổng của RMI Registry trên Server
    private static final String RMI_HOST = "localhost";
    private static final int RMI_PORT = 1098;
    private static final String RMI_SERVICE_NAME = "RMIService";

    public RMIClientService() {
        try {
            System.out.println(">> Client connecting to RMI Registry...");

            // Bước 1: Tra cứu dịch vụ (Lookup)
            String url = "rmi://" + RMI_HOST + ":" + RMI_PORT + "/" + RMI_SERVICE_NAME;

            // Ép kiểu đối tượng trả về thành giao diện Remote của chúng ta
            remoteService = (RMIService) Naming.lookup(url);

            System.out.println(">> RMI Service lookup successful!");

        } catch (Exception e) {
            System.err.println("!! RMI Client Service Initialization failed:");
            e.printStackTrace();
            // Đảm bảo remoteService là null nếu lookup thất bại
            remoteService = null;
        }
    }
    public int countReports(int userId) {
        try {
            return remoteService.countReports(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int countPendingReports(int userId) {
        try {
            return remoteService.countPendingReports(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public RMIService getRemoteService() {
        return remoteService;
    }
}