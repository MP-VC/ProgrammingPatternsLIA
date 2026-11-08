package vanier.com.programmingpatternslia.DataBase.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import vanier.com.programmingpatternslia.Model.User;
import vanier.com.programmingpatternslia.Error.UserError;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    @TempDir
    Path directory;
    private UserDAO dao;
    private String url;
    @BeforeEach
    void setUp() throws SQLException {
        url = "jdbc:sqlite:" + directory.resolve("registration-test.db");
        dao = UserDAO.forTesting(url);
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("CREATE TABLE USER (" +
                    "U_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "FullName TEXT NOT NULL," +
                    "Age INTEGER," +
                    "Role TEXT CHECK(Role IN ('ADMIN', 'PARTICIPANT')))");
            stmt.execute("CREATE TABLE IF NOT EXISTS CREDENTIALS (" +
                    "Cred_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "U_ID INTEGER UNIQUE, " +
                    "Email TEXT UNIQUE NOT NULL, " +
                    "Password TEXT NOT NULL, " +
                    "FOREIGN KEY (U_ID) REFERENCES USER(U_ID) ON DELETE CASCADE)");
            stmt.execute(
                "INSERT INTO USER (FullName, Age, Role) " +
                    "VALUES ('John Doe', 20, 'PARTICIPANT')");
        }
    }

    @Test
    void createUser() throws SQLException {
        assertDoesNotThrow(() -> dao.createUser("John Doe", 20, "PARTICIPANT"));
        assertDoesNotThrow(() -> dao.createUser("Jane Doe", 20, "ADMIN"));
        assertNotNull(dao.getUserById(1));
        assertNotNull(dao.getUserById(2));
    }

    @Test
    void getUserByEmail() throws SQLException{
        String sql = "INSERT INTO CREDENTIALS (U_ID, Email, Password) VALUES (1, 'Johndoe@email.com', 'password')";
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        };
        User user = dao.getUserByEmail("Johndoe@email.com");
        assertNotNull(user);
        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void getUserById() throws SQLException{
        assertNotNull(dao.getUserById(1));
    }

    @Test
    void updateUser() {
        assertDoesNotThrow(() ->dao.updateUser(new User(1,"Joe Doe", 50,"ADMIN")));
    }

    @Test
    void deleteUser() {
        assertDoesNotThrow(() ->dao.deleteUser(1));
    }

    @Test
    void getAllUsers() throws SQLException{
        assertNotNull(dao.getAllUsers());
        assertEquals(1, dao.getAllUsers().size());
    }
}