package vanier.com.programmingpatternslia.DataBase.Registration;

import vanier.com.programmingpatternslia.DataBase.DataBaseCore;
import vanier.com.programmingpatternslia.Model.Registration;

import java.sql.*;

public class RegistrationDAO {
    private static RegistrationDAO registrationDAO;
    private static String path;

    private RegistrationDAO() {}

    public static RegistrationDAO get() {
        if (!DataBaseCore.isInitialized()) {
            DataBaseCore.StartUp();
        }
        if (registrationDAO == null) {
            registrationDAO = new RegistrationDAO();
            path = DataBaseCore.getUrl();
        }
        return registrationDAO;
    }

    public void add(int conferenceID, int userID, String status) throws SQLException {
        String sql = "INSERT INTO REGISTRATION(C_ID, U_ID, Status, WaitingList, WaitingListPosition) VALUES (?, ?, ?, 0, 0)";

        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, conferenceID);
            pstmt.setInt(2, userID);
            pstmt.setString(3, status.toUpperCase()); // Keeps it sanitized for your CHECK constraint

            pstmt.executeUpdate();
        }
    }

    public Registration findById(int registrationId) throws SQLException {
        String sql = "SELECT * FROM REGISTRATION WHERE R_ID = ?";

        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registrationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Registration(
                            rs.getInt("R_ID"),
                            rs.getInt("C_ID"),
                            rs.getInt("U_ID"),
                            rs.getInt("WaitingList"),
                            rs.getString("Status"),
                            rs.getInt("WaitingListPosition")
                    );
                }
            }
        }
        return null;
    }

    public void updateStatus(int registrationID, String newStatus, int newPosition) throws SQLException {
        String sql = "UPDATE REGISTRATION SET Status = ?, WaitingListPosition = ? WHERE R_ID = ?";

        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus.toUpperCase());
            pstmt.setInt(2, newPosition);
            pstmt.setInt(3, registrationID);

            pstmt.executeUpdate();
        }
    }

    public void cancelRegistration(int registrationID) throws SQLException {
        String sql = "DELETE FROM REGISTRATION WHERE R_ID = ?";

        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registrationID);

            pstmt.executeUpdate();
        }
    }

}
