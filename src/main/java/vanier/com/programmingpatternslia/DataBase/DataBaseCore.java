package vanier.com.programmingpatternslia.DataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCore {

    private static boolean initialize = false;
    private static final String URL = "jdbc:sqlite:src/main/resources/vanier.com.programming patterns/pjDataBase.db";

    public static String getUrl()
    {
        if (!initialize) {
            StartUp();
        }
        return URL;
    }
    public static boolean isInitialized()
    {
        return initialize;
    }
    public static void StartUp() {
        if (initialize) return;

        ensureDirectoriesExist();

        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                createInitialTables(conn);
                initialize = true;
                createAdminAccount();
            }
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }

    private static void ensureDirectoriesExist() {
        String filePath = URL.replace("jdbc:sqlite:", "");
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    private static void createInitialTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;"); //I use this to enable foreign keys cause sqlLight disables them by default

            // Create USER Table
            String userTable = "CREATE TABLE IF NOT EXISTS USER ("
                    + "U_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "FullName TEXT NOT NULL, "
                    + "Age INTEGER, "
                    + "Role TEXT CHECK(Role IN ('ADMIN', 'PARTICIPANT'))"
                    + ");";
            stmt.execute(userTable);

            // Create CREDENTIALS Table
            String credentialsTable = "CREATE TABLE IF NOT EXISTS CREDENTIALS ("
                    + "Cred_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "U_ID INTEGER UNIQUE, "
                    + "Email TEXT UNIQUE NOT NULL, "
                    + "Password TEXT NOT NULL, "
                    + "FOREIGN KEY (U_ID) REFERENCES USER(U_ID) ON DELETE CASCADE"
                    + ");";
            stmt.execute(credentialsTable);

            // Create CONFERENCE Table
            String conferenceTable = "CREATE TABLE IF NOT EXISTS CONFERENCE ("
                    + "C_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "Title TEXT NOT NULL, "
                    + "Capacity INTEGER NOT NULL, "
                    + "Start_DateTime TEXT NOT NULL, " // SQLite uses TEXT/NUMERIC for DateTimes
                    + "End_DateTime TEXT NOT NULL, "
                    + "Location TEXT, "
                    + "waitingList INTEGER DEFAULT 0"
                    + ");";
            stmt.execute(conferenceTable);

            // Create REGISTRATION Table
            String registrationTable = "CREATE TABLE IF NOT EXISTS REGISTRATION ("
                    + "R_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "C_ID INTEGER, "
                    + "U_ID INTEGER, "
                    + "WaitingList INTEGER DEFAULT 0, "
                    + "Status TEXT CHECK(Status IN ('CONFIRMED', 'WAITLISTED')), "
                    + "WaitingListPosition INTEGER, "
                    + "FOREIGN KEY (C_ID) REFERENCES CONFERENCE(C_ID) ON DELETE CASCADE, "
                    + "FOREIGN KEY (U_ID) REFERENCES USER(U_ID) ON DELETE CASCADE, "
                    + "UNIQUE(C_ID, U_ID)" // Ensures a user cannot register for the same conference twice
                    + ");";
            stmt.execute(registrationTable);

        }
    }

    private static void createAdminAccount() throws SQLException {
        String sqlUSRTable = "INSERT INTO USER VALUES('ADMIN',1,'ADMIN')";
        String sqlCRDTable = "INSERT INTO CREDENTIALS VALUES(1,'admin@admin.ca','4Dm!n')";
        try(Connection con = DriverManager.getConnection(URL);
            Statement stmt = con.createStatement()) {
            stmt.execute(sqlUSRTable);
            stmt.execute(sqlCRDTable);
        }
    }

}
