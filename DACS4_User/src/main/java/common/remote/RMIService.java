package common.remote;

import common.model.Admin;
import common.model.Report;
import common.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIService extends Remote {
    Admin loginAdmin(String email, String password) throws RemoteException;

    // ---------- USER ----------
    boolean register(User user) throws RemoteException;

    User login(String email, String password) throws RemoteException;

    boolean updateUserProfile(User user) throws RemoteException;

    int countReports(int userId) throws RemoteException;

    int countPendingReports(int userId) throws RemoteException;

    // ---------- REPORT ----------
    boolean sendReport(Report report) throws RemoteException;

    List<Report> getReportList(int userId) throws RemoteException;

    Report getReportDetail(int reportId) throws RemoteException;

    boolean updateReportStatus(int reportId, String status) throws RemoteException;

    boolean sendFeedback(int reportId, String feedback) throws RemoteException;

    List<Report> getAllReports() throws RemoteException;

    // ---------- ADMIN / USER MANAGEMENT ----------
    List<User> getUserList() throws RemoteException;

    boolean addUser(User user) throws RemoteException;

    boolean updateUser(User user) throws RemoteException;

    boolean deleteUser(int userId) throws RemoteException;
}