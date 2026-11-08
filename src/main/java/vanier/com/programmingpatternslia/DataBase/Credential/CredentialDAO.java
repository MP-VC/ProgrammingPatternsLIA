package vanier.com.programmingpatternslia.DataBase.Credential;

import vanier.com.programmingpatternslia.DataBase.DataBaseCore;

import vanier.com.programmingpatternslia.Error.CredentialError;

import java.sql.*;

public class CredentialDAO {
    private final String path;

    private CredentialDAO(String path) {
        this.path = path;
    }
    static CredentialDAO forTesting(String url) {
        return new CredentialDAO(url);
    }

    public void createPassword(int u_ID, String email, String password) throws SQLException {
        if(password.length() < 8) throw new CredentialError("Password too short");
        String sql = "INSERT INTO CREDENTIALS (U_ID, Email, Password) VALUES (?,?,?)";
        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            password = makeHashedPassword(password);

            pstmt.setInt(1, u_ID);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            pstmt.executeUpdate();
        }
    }
    public void updatePassword(String email, String password) throws SQLException {
        if(password.length() < 8) throw new CredentialError("Password too short");
        String sql = "UPDATE CREDENTIALS SET Password = ? WHERE Email = ?";
        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            password = makeHashedPassword(password);
            pstmt.setString(1, password);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        }
    }
    public boolean verifyPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM CREDENTIALS WHERE Email = ?";
        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql);
            ) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("Password");
                    return storedPassword != null &&
                            storedPassword.equals(makeHashedPassword(password));
                }
            }
        }
        return false;
    }
    private String makeHashedPassword(String password) {;
        return "P" + password.hashCode()*351+1000;
    }
}