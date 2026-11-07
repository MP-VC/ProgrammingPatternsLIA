
package vanier.com.programmingpatternslia.DataBase.Registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import vanier.com.programmingpatternslia.Model.Registration;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationDAOTest {

    @TempDir
    Path directory;

    private RegistrationDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        String url = "jdbc:sqlite:" + directory.resolve("registration-test.db");
        dao = RegistrationDAO.forTesting(url);

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("CREATE TABLE USER (" +
                    "U_ID INTEGER PRIMARY KEY, " +
                    "FullName TEXT NOT NULL)");

            stmt.execute("CREATE TABLE CONFERENCE (" +
                    "C_ID INTEGER PRIMARY KEY, " +
                    "Title TEXT NOT NULL)");

            stmt.execute("CREATE TABLE REGISTRATION (" +
                    "R_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "C_ID INTEGER NOT NULL, " +
                    "U_ID INTEGER NOT NULL, " +
                    "WaitingList INTEGER NOT NULL DEFAULT 0 CHECK(WaitingList IN (0,1)), " +
                    "Status TEXT NOT NULL CHECK(Status IN ('CONFIRMED','WAITLISTED')), " +
                    "WaitingListPosition INTEGER NOT NULL DEFAULT 0 CHECK(WaitingListPosition >= 0), " +
                    "FOREIGN KEY(C_ID) REFERENCES CONFERENCE(C_ID), " +
                    "FOREIGN KEY(U_ID) REFERENCES USER(U_ID), " +
                    "UNIQUE(C_ID,U_ID))");

            stmt.execute("INSERT INTO USER VALUES (20, 'Test User')");
            stmt.execute("INSERT INTO CONFERENCE VALUES (10, 'Test Conference')");
        }
    }

    @Test
    void add() throws SQLException {
        dao.add(10, 20, "CONFIRMED");

        Registration registration = dao.findById(1);

        assertNotNull(registration);
        assertEquals("CONFIRMED", registration.getStatus());
        assertEquals(0, registration.getWaitingList());
        assertEquals(0, registration.getWaitingListPosition());
    }

    @Test
    void findById() throws SQLException {
        dao.add(10, 20, "CONFIRMED");

        Registration registration = dao.findById(1);

        assertNotNull(registration);
        assertEquals(1, registration.getRegistrationId());
        assertEquals(10, registration.getConferenceId());
        assertEquals(20, registration.getUserId());

        assertNull(dao.findById(999));
    }

    @Test
    void updateStatus() throws SQLException {
        dao.add(10, 20, "CONFIRMED");
        dao.updateStatus(1, "WAITLISTED", 2);

        Registration updated = dao.findById(1);

        assertNotNull(updated);
        assertEquals("WAITLISTED", updated.getStatus());
        assertEquals(1, updated.getWaitingList());
        assertEquals(2, updated.getWaitingListPosition());
    }

    @Test
    void cancelRegistration() throws SQLException {
        dao.add(10, 20, "CONFIRMED");

        assertNotNull(dao.findById(1));

        dao.cancelRegistration(1);

        assertNull(dao.findById(1));
    }

    @Test
    void rejectsDuplicateRegistration() throws SQLException {
        dao.add(10, 20, "CONFIRMED");

        assertThrows(SQLException.class, () -> {
            dao.add(10, 20, "CONFIRMED");
        });
    }
}
