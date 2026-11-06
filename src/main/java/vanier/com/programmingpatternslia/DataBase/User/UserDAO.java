package vanier.com.programmingpatternslia.DataBase.User;

import vanier.com.programmingpatternslia.DataBase.DataBaseCore;
import vanier.com.programmingpatternslia.Error.UserError;
import vanier.com.programmingpatternslia.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static String path = DataBaseCore.getUrl();
    public void createUser(String fullName,int age,String role) throws SQLException {
        if ((age < 0 || age > 100) && (role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("PARTICIPANT"))) {
            String sql = "INSERT INTO USERS (FullName, Age, Role) VALUES (?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(path);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, fullName);
                pstmt.setInt(2, age);
                pstmt.setString(3, role);

                pstmt.executeUpdate();
            }
        }
        else throw new UserError("Incorrect age or role");
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT u.U_ID, u.FullName, u.Age, u.Role, c.Email, c.Password FROM USER u INNER JOIN CREDENTIALS c ON u.U_ID = c.U_ID WHERE c.Email = ?";
        User user = null;
        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("U_ID"),
                            rs.getString("FullName"),
                            rs.getInt("Age"),
                            rs.getString("Role")
                    );
                }
            }
        }
        if(user!=null) return user;
        else throw new UserError("User not found");
    }
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE U_ID = ?";
        User user = null;
        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("U_ID"),
                            rs.getString("FullName"),
                            rs.getInt("Age"),
                            rs.getString("Role")
                    );
                }
            }
        }
        if(user!=null) return user;
        else throw new UserError("User not found");
    }

    public void updateUser(User user) throws SQLException {
        if ((user.getAge() < 0 || user.getAge() > 100) && (user.getRole().equalsIgnoreCase("ADMIN") || user.getRole().equalsIgnoreCase("PARTICIPANT"))) {
            String sql = "UPDATE USER SET FullName = ?, Age = ?, Role = ? WHERE U_ID = ?";
            try (Connection conn = DriverManager.getConnection(path);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, user.getFullName());
                pstmt.setInt(2, user.getAge());
                pstmt.setString(3, user.getRole());
                pstmt.setInt(3, user.getUserId());

                pstmt.executeUpdate();
            }
        }
        else throw new UserError("Incorrect age or role");
    }

    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM USERS WHERE U_ID = ?";
        try (Connection conn = DriverManager.getConnection(path);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS";
        try (Connection conn = DriverManager.getConnection(path);
                Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                 users.add(new User(rs.getInt("U_ID"), rs.getString("FullName"), rs.getInt("Age"), rs.getString("Role")));
            }
        }
        return users;
    }
}