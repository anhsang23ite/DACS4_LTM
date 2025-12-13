package impl;
import common.model.*;
import common.remote.RMIService;
import server.DatabaseConnection;
import java.util.Date;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RMIServiceImpl extends UnicastRemoteObject implements RMIService {
    public RMIServiceImpl() throws RemoteException { super(); }

    /* LƯU Ý: Phương thức login() đã bị xóa vì nó KHÔNG còn trong giao diện RMIService */
    @Override
    public boolean register(User user) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users(name, email, password) VALUES(?,?,?)"
            );
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User login(String email, String password) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=?"
            );
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("userId"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                return u;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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


            PreparedStatement psRep = conn.prepareStatement("INSERT INTO reports(userId, title, description,image, locationId, status, createdAt) VALUES(?,?,?,?,?,?,NOW())");
            psRep.setInt(1, report.getUserId());
            psRep.setString(2, report.getTitle());
            psRep.setString(3, report.getDescription());
            psRep.setString(4, report.getImage());
            psRep.setInt(5, locId);
            psRep.setString(6, "PENDING");
            return psRep.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }


    @Override
    // Đã đổi tên từ getMyReports hoặc getReportList(String status) thành getReportList(int userId)
    public List<Report> getReportList(int userId) throws RemoteException {
        List<Report> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT r.*, l.address, l.latitude, l.longitude, r.feedback FROM reports r LEFT JOIN locations l ON r.locationId = l.locationId WHERE r.userId=? ORDER BY r.createdAt ASC");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Report r = new Report();
                r.setReportId(rs.getInt("reportId"));
                r.setUserId(rs.getInt("userId"));
                r.setTitle(rs.getString("title"));
                r.setDescription(rs.getString("description"));

                Location loc = new Location();
                loc.setAddress(rs.getString("address"));
                loc.setLatitude(rs.getDouble("latitude"));
                loc.setLongitude(rs.getDouble("longitude"));
                r.setLocation(loc);

                r.setStatus(rs.getString("status"));
                r.setFeedback(rs.getString("feedback"));

                Timestamp ts = rs.getTimestamp("createdAt");
                if (ts != null) {
                    r.setCreatedAt(new Date(ts.getTime()));
                }
                list.add(r);
            }
        } catch (Exception e){ e.printStackTrace(); }
        return list;
    }

    /* LƯU Ý: Phương thức getAllReports() đã bị xóa vì nó KHÔNG còn trong giao diện RMIService */

    @Override
    public Report getReportDetail(int reportId) throws RemoteException {
        Report r = null;
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT r.*, l.address, l.latitude, l.longitude, r.feedback " +
                            "FROM reports r " +
                            "LEFT JOIN locations l ON r.locationId = l.locationId " +
                            "WHERE r.reportId=?"
            );
            ps.setInt(1, reportId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                r = new Report();
                r.setReportId(rs.getInt("reportId"));
                r.setUserId(rs.getInt("userId"));
                r.setTitle(rs.getString("title"));
                r.setDescription(rs.getString("description"));
                r.setStatus(rs.getString("status"));
                r.setFeedback(rs.getString("feedback"));

                Location loc = new Location();
                loc.setAddress(rs.getString("address"));
                loc.setLatitude(rs.getDouble("latitude"));
                loc.setLongitude(rs.getDouble("longitude"));
                r.setLocation(loc);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return r;
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

    @Override
    public int countReports(int userId) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM reports WHERE userId = ?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public int countPendingReports(int userId) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM reports WHERE userId = ? AND status = 'PENDING'"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

}