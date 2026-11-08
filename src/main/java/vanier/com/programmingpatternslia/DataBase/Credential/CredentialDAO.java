package vanier.com.programmingpatternslia.DataBase.Credential;

import vanier.com.programmingpatternslia.DataBase.DataBaseCore;
import vanier.com.programmingpatternslia.Error.CredentialError;

import java.sql.*;

public class CredentialDAO {
    private static String path = DataBaseCore.getUrl();
    public void createPassword(String fullName, int u_ID, String email, String password) throws Exception {
        if(password.length() < 8) throw new CredentialError("Password too short");
        String sql = "INSERT INTO CREDENTIALS (FullName, U_ID, Email, Password) VALUES (?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            password = makeHashedPassword(password);

            pstmt.setString(1, fullName);
            pstmt.setInt(2, u_ID);
            pstmt.setString(3, email);
            pstmt.setString(4, password);

            pstmt.executeUpdate();
        }
    }
    public void updatePassword(String email, String password) throws Exception {
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

    public boolean verifyPassword(String email, String password) throws Exception {
        String sql = "SELECT * FROM CREDENTIALS WHERE Email = ?";
        try (Connection conn = DriverManager.getConnection(path);
             ResultSet rs = conn.prepareStatement(sql).executeQuery();
             PreparedStatement pstmt = conn.prepareStatement(sql)
            ) {
            pstmt.setString(1, email);
            if((rs.getString("Password")).equals(makeHashedPassword(password))) return true;
        }
        return false;
    }

    private String makeHashedPassword(String password) {;
        return "P" + password.hashCode()*351+1000;
    }
}
