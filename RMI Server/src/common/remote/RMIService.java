package common.remote;
import common.model.Report;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
public interface RMIService extends Remote {
    boolean sendReport(Report report) throws RemoteException;
    List<Report> getReportList(int userId) throws RemoteException;
    Report getReportDetail(int reportId) throws RemoteException;
    boolean updateReportStatus(int reportId, String status) throws RemoteException;
    boolean sendFeedback(int reportId, String feedback) throws RemoteException;
}