package server;
import common.RMIService;
import common.model.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RMIServiceImpl extends UnicastRemoteObject implements RMIService {
    public RMIServiceImpl() throws RemoteException { super(); }

    @Override
    public User login(String email, String password) throws RemoteException {
        System.out.println("[Server] User login request: " + email);
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
            ps.setString(1, email); ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("userId"));
                u.setName(rs.getString("name"));
                return u;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean register(User user) throws RemoteException {
        // Logic insert user... (Bạn tự implement tương tự login nhé)
        return true;
    }

    @Override
    public boolean sendReport(Report report) throws RemoteException {
        System.out.println("[Server] Receiving report from UserID: " + report.getUserId());
        try (Connection conn = DatabaseConnection.getConnection()) {
            // 1. Insert Location trước để lấy locationId
            PreparedStatement psLoc = conn.prepareStatement("INSERT INTO locations(address) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            psLoc.setString(1, report.getLocation().getAddress());
            psLoc.executeUpdate();
            ResultSet rs = psLoc.getGeneratedKeys();
            int locId = rs.next() ? rs.getInt(1) : 0;

            // 2. Insert Report
            PreparedStatement psRep = conn.prepareStatement("INSERT INTO reports(userId, title, description, locationId, status) VALUES(?,?,?,?,?)");
            psRep.setInt(1, report.getUserId());
            psRep.setString(2, report.getTitle());
            psRep.setString(3, report.getDescription());
            psRep.setInt(4, locId);
            psRep.setString(5, "PENDING");
            return psRep.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    @Override
    public List<Report> getMyReports(int userId) throws RemoteException {
        // Logic select * from reports where userId = ...
        return new ArrayList<>();
    }

    @Override
    public boolean updateReportStatus(int reportId, String status) throws RemoteException {
        // Logic update admin
        return true;
    }
}