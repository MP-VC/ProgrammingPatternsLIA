package vanier.com.programmingpatternslia.DataBase.User;

import vanier.com.programmingpatternslia.DataBase.DataBaseCore;
import vanier.com.programmingpatternslia.Error.UserError;
import vanier.com.programmingpatternslia.Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static String path = DataBaseCore.getUrl();
    public void createUser(String fullName,int age,String role)  throws SQLException {
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

    public User getUserByEmail(String email) {
        return null;
    }

    public User getUserById(int userId) {
        return null;
    }

    public void updateUser(User user) {
    }

    public void deleteUser(int userId) {
    }

    public List<User> getAllUsers() {
        return new ArrayList<>();
    }
}