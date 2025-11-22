package common;
import common.model.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIService extends Remote {
    User login(String email, String password) throws RemoteException;
    boolean register(User user) throws RemoteException;

    boolean sendReport(Report report) throws RemoteException;
    List<Report> getMyReports(int userId) throws RemoteException;

    // Admin functions
    boolean updateReportStatus(int reportId, String status) throws RemoteException;
}