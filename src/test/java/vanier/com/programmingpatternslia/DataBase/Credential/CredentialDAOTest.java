
package vanier.com.programmingpatternslia.DataBase.Credential;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class CredentialDAOTest {

    @TempDir
    Path directory;

    private CredentialDAO dao;
    private String databaseUrl;

    @BeforeEach
    void setUp() throws SQLException {

        // Create a separate temporary database for each test
        databaseUrl = "jdbc:sqlite:" +
                directory.resolve("registration-test.db");

        dao = CredentialDAO.forTesting(databaseUrl);

        try (Connection conn = DriverManager.getConnection(databaseUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
            // Create USER table
            stmt.execute("CREATE TABLE USER (\n" +
                         "    U_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                         "    FullName TEXT NOT NULL,\n" +
                         "    Age INTEGER,\n" +
                         "    Role TEXT CHECK(Role IN ('ADMIN', 'PARTICIPANT'))\n" +
                         ")\n");

            // Create CREDENTIALS table
            stmt.execute("CREATE TABLE CREDENTIALS (\n" +
                         "    Cred_ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                         "    U_ID INTEGER UNIQUE,\n" +
                         "    Email TEXT UNIQUE NOT NULL,\n" +
                         "    Password TEXT NOT NULL,\n" +
                         "    FOREIGN KEY (U_ID)\n" +
                         "        REFERENCES USER(U_ID)\n" +
                         "        ON DELETE CASCADE\n" +
                         ")\n");

            // Insert a test user
            stmt.execute("INSERT INTO USER (FullName, Age, Role)\n" +
                         "VALUES ('John Doe', 40, 'ADMIN')\n");
        }
    }

    @Test
    void w() throws SQLException {

        assertDoesNotThrow(() ->
                dao.createPassword(
                        1,
                        "jd@email.com",
                        "superrrPASSWORD"
                )
        );

        assertTrue(
                dao.verifyPassword(
                        "jd@email.com",
                        "superrrPASSWORD"
                ),
                "Password should be created successfully"
        );
    }

    @Test
    void a() throws SQLException {

        // First create the original password
        dao.createPassword(
                1,
                "jd@email.com",
                "superrrPASSWORD"
        );

        // Update the password
        assertDoesNotThrow(() ->
                dao.updatePassword(
                        "jd@email.com",
                        "SUPERDUPERPASSWORDDD"
                )
        );

        // Verify the new password works
        assertTrue(
                dao.verifyPassword(
                        "jd@email.com",
                        "SUPERDUPERPASSWORDDD"
                ),
                "Updated password should be valid"
        );

        // Verify the old password no longer works
        assertFalse(
                dao.verifyPassword(
                        "jd@email.com",
                        "superrrPASSWORD"
                ),
                "Old password should no longer be valid"
        );
    }

    @Test
    void b() throws SQLException {

        dao.createPassword(
                1,
                "jd@email.com",
                "superrrPASSWORD"
        );

        assertTrue(
                dao.verifyPassword(
                        "jd@email.com",
                        "superrrPASSWORD"
                ),
                "Should verify the correct password"
        );
    }

    @Test
    void h() throws SQLException {

        dao.createPassword(
                1,
                "jd@email.com",
                "superrrPASSWORD"
        );

        assertFalse(
                dao.verifyPassword(
                        "jd@email.com",
                        "SUPERINCORRECTPASSWORD"
                ),
                "Should reject an incorrect password"
        );
    }
}
