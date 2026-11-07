
package vanier.com.programmingpatternslia.DataBase.Registration;

import vanier.com.programmingpatternslia.DataBase.DataBaseCore;
import vanier.com.programmingpatternslia.Model.Registration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationDAO {
    private static final Logger LOGGER = Logger.getLogger(RegistrationDAO.class.getName());
    private static RegistrationDAO registrationDAO;
    private final String path;

    private RegistrationDAO(String path) {
        this.path = path;
    }

    public static RegistrationDAO get() {
        if (!DataBaseCore.isInitialized()) {
            DataBaseCore.StartUp();
        }
        if (registrationDAO == null) {
            registrationDAO = new RegistrationDAO(DataBaseCore.getUrl());
        }
        return registrationDAO;
    }

    static RegistrationDAO forTesting(String url) {
        return new RegistrationDAO(url);
    }

    private Connection openConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(path);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            conn.close();
            throw e;
        }
        return conn;
    }

    private static String validStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }

        String value = status.toUpperCase(Locale.ROOT);

        if (!value.equals("CONFIRMED") && !value.equals("WAITLISTED")) {
            throw new IllegalArgumentException("Status must be CONFIRMED or WAITLISTED");
        }

        return value;
    }

    public void add(int conferenceID, int userID, String status) throws SQLException {
        String value = validStatus(status);

        String sql = "INSERT INTO REGISTRATION(C_ID, U_ID, Status, WaitingList, WaitingListPosition) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, conferenceID);
            pstmt.setInt(2, userID);
            pstmt.setString(3, value);
            pstmt.setInt(4, value.equals("WAITLISTED") ? 1 : 0);
            pstmt.setInt(5, 0);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not add registration", e);
            throw e;
        }
    }

    private Registration fromResult(ResultSet rs) throws SQLException {
        return new Registration(
                rs.getInt("R_ID"),
                rs.getInt("C_ID"),
                rs.getInt("U_ID"),
                rs.getInt("WaitingList"),
                rs.getString("Status"),
                rs.getInt("WaitingListPosition")
        );
    }

    public Registration findById(int registrationId) throws SQLException {
        String sql = "SELECT * FROM REGISTRATION WHERE R_ID = ?";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registrationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? fromResult(rs) : null;
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not find registration", e);
            throw e;
        }
    }

    public List<Registration> findByConference(int conferenceID) throws SQLException {
        return findAll(
                "SELECT * FROM REGISTRATION WHERE C_ID = ? ORDER BY R_ID",
                conferenceID
        );
    }

    public List<Registration> findByUser(int userID) throws SQLException {
        return findAll(
                "SELECT * FROM REGISTRATION WHERE U_ID = ? ORDER BY R_ID",
                userID
        );
    }

    public List<Registration> findWaitingList(int conferenceID) throws SQLException {
        return findAll(
                "SELECT * FROM REGISTRATION WHERE C_ID = ? AND Status = 'WAITLISTED' ORDER BY WaitingListPosition, R_ID",
                conferenceID
        );
    }

    private List<Registration> findAll(String sql, int id) throws SQLException {
        List<Registration> results = new ArrayList<>();

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(fromResult(rs));
                }
            }

            return results;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not list registrations", e);
            throw e;
        }
    }

    public void updateStatus(int registrationID, String newStatus, int newPosition) throws SQLException {
        String value = validStatus(newStatus);

        if (newPosition < 0) {
            throw new IllegalArgumentException("Waiting-list position cannot be negative");
        }

        if (value.equals("CONFIRMED") && newPosition != 0) {
            throw new IllegalArgumentException("Confirmed registrations must have position 0");
        }

        String sql = "UPDATE REGISTRATION SET Status = ?, WaitingList = ?, WaitingListPosition = ? WHERE R_ID = ?";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, value);
            pstmt.setInt(2, value.equals("WAITLISTED") ? 1 : 0);
            pstmt.setInt(3, newPosition);
            pstmt.setInt(4, registrationID);

            if (pstmt.executeUpdate() == 0) {
                throw new SQLException("Registration not found: " + registrationID);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not update registration", e);
            throw e;
        }
    }

    public void cancelRegistration(int registrationID) throws SQLException {
        String sql = "DELETE FROM REGISTRATION WHERE R_ID = ?";

        try (Connection conn = openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registrationID);

            if (pstmt.executeUpdate() == 0) {
                throw new SQLException("Registration not found: " + registrationID);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not cancel registration", e);
            throw e;
        }
    }
}
