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

    public RMIServiceImpl() throws RemoteException {
        super();
    }

    // ================= USER ACCOUNT =================

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateUserProfile(User user) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET name = ?, email = ? WHERE userId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= ADMIN: USER MANAGEMENT =================

    @Override
    public List<User> getUserList() throws RemoteException {
        List<User> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("userId"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addUser(User user) throws RemoteException {
        return register(user);
    }

    @Override
    public boolean updateUser(User user) throws RemoteException {
        return updateUserProfile(user);
    }

    @Override
    public boolean deleteUser(int userId) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE userId = ?");
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Admin loginAdmin(String email, String password) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Admin ad = new Admin();
                ad.setAdminId(rs.getInt("adminId"));
                ad.setName(rs.getString("name"));
                ad.setEmail(rs.getString("email"));
                return ad;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= REPORT METHODS =================

    @Override
    public boolean sendReport(Report report) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // 1. Lưu vị trí
            PreparedStatement psLoc = conn.prepareStatement(
                    "INSERT INTO locations(address, latitude, longitude) VALUES(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            psLoc.setString(1, report.getLocation().getAddress());
            psLoc.setDouble(2, report.getLocation().getLatitude());
            psLoc.setDouble(3, report.getLocation().getLongitude());
            psLoc.executeUpdate();

            ResultSet rs = psLoc.getGeneratedKeys();
            int locId = rs.next() ? rs.getInt(1) : 0;

            // 2. Lưu báo cáo
            PreparedStatement psRep = conn.prepareStatement(
                    "INSERT INTO reports(userId, title, description, image, locationId, status, createdAt) VALUES(?,?,?,?,?,?,NOW())"
            );
            psRep.setInt(1, report.getUserId());
            psRep.setString(2, report.getTitle());
            psRep.setString(3, report.getDescription());
            psRep.setString(4, (report.getImage() != null) ? report.getImage() : "");
            psRep.setInt(5, locId);
            psRep.setString(6, "PENDING");

            return psRep.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Report> getReportList(int userId) throws RemoteException {
        List<Report> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT r.*, l.address, l.latitude, l.longitude FROM reports r " +
                            "LEFT JOIN locations l ON r.locationId = l.locationId " +
                            "WHERE r.userId=? ORDER BY r.createdAt DESC"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Report r = new Report();
                fillReportData(r, rs);
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Report getReportDetail(int reportId) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT r.*, l.address, l.latitude, l.longitude FROM reports r " +
                            "LEFT JOIN locations l ON r.locationId = l.locationId WHERE r.reportId=?"
            );
            ps.setInt(1, reportId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Report r = new Report();
                fillReportData(r, rs);
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= STATUS & FEEDBACK =================

    @Override
    public boolean updateReportStatus(int reportId, String status) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE reports SET status=? WHERE reportId=?");
            ps.setString(1, status);
            ps.setInt(2, reportId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean sendFeedback(int reportId, String feedback) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE reports SET feedback=? WHERE reportId=?");
            ps.setString(1, feedback);
            ps.setInt(2, reportId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= STATS =================

    @Override
    public int countReports(int userId) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM reports WHERE userId = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int countPendingReports(int userId) throws RemoteException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM reports WHERE userId = ? AND status = 'PENDING'");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Hàm hỗ trợ đổ dữ liệu vào đối tượng Report
    private void fillReportData(Report r, ResultSet rs) throws SQLException {
        r.setReportId(rs.getInt("reportId"));
        r.setUserId(rs.getInt("userId"));
        r.setTitle(rs.getString("title"));
        r.setDescription(rs.getString("description"));
        r.setImage(rs.getString("image"));
        r.setStatus(rs.getString("status"));
        r.setFeedback(rs.getString("feedback"));

        Location loc = new Location();
        loc.setAddress(rs.getString("address"));
        loc.setLatitude(rs.getDouble("latitude"));
        loc.setLongitude(rs.getDouble("longitude"));
        r.setLocation(loc);

        Timestamp ts = rs.getTimestamp("createdAt");
        if (ts != null) r.setCreatedAt(new Date(ts.getTime()));
    }
}