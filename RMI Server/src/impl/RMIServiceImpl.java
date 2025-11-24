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
                u.setEmail(rs.getString("email"));
                return u;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean sendReport(Report report) throws RemoteException {
        System.out.println("[Server] Receiving report from UserID: " + report.getUserId());
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement psLoc = conn.prepareStatement("INSERT INTO locations(address,latitude,longitude) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            psLoc.setString(1, report.getLocation().getAddress());
            psLoc.setDouble(2, report.getLocation().getLatitude());
            psLoc.setDouble(3, report.getLocation().getLongitude());
            psLoc.executeUpdate();
            ResultSet rs = psLoc.getGeneratedKeys();
            int locId = rs.next() ? rs.getInt(1) : 0;


            PreparedStatement psRep = conn.prepareStatement("INSERT INTO reports(userId, title, description, locationId, status, createdAt) VALUES(?,?,?,?,?,NOW())");
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
        List<Report> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT r.*, l.address, l.latitude, l.longitude FROM reports r LEFT JOIN locations l ON r.locationId = l.locationId WHERE r.userId=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Report r = new Report();
                r.setReportId(rs.getInt("reportId"));
                r.setUserId(rs.getInt("userId"));
                r.setTitle(rs.getString("title"));
                r.setDescription(rs.getString("description"));
                Location loc = new Location(); loc.setAddress(rs.getString("address")); loc.setLatitude(rs.getDouble("latitude")); loc.setLongitude(rs.getDouble("longitude"));
                r.setLocation(loc);
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (Exception e){ e.printStackTrace(); }
        return list;
    }

    @Override
    public List<Report> getAllReports() throws RemoteException {
        List<Report> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT r.*, l.address FROM reports r LEFT JOIN locations l ON r.locationId = l.locationId ORDER BY r.createdAt DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Report r = new Report();
                r.setReportId(rs.getInt("reportId"));
                r.setUserId(rs.getInt("userId"));
                r.setTitle(rs.getString("title"));
                r.setDescription(rs.getString("description"));
                Location loc = new Location(); loc.setAddress(rs.getString("address"));
                r.setLocation(loc);
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (Exception e){ e.printStackTrace(); }
        return list;
    }


    @Override
    public boolean updateReportStatus(int reportId, String status) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement("UPDATE reports SET status=? WHERE reportId=?");
            ps.setString(1, status); ps.setInt(2, reportId);
            return ps.executeUpdate() > 0;
        } catch (Exception e){ e.printStackTrace(); }
        return false;
    }


    @Override
    public boolean sendFeedback(int reportId, String feedback) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement("UPDATE reports SET feedback=? WHERE reportId=?");
            ps.setString(1, feedback); ps.setInt(2, reportId);
            return ps.executeUpdate() > 0;
        } catch (Exception e){ e.printStackTrace(); }
        return false;
    }
    }