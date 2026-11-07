
package vanier.com.programmingpatternslia.DataBase;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseCore {
    private static final Logger LOGGER = Logger.getLogger(DataBaseCore.class.getName());
    private static final String URL = "jdbc:sqlite:src/main/resources/vanier.com.programming patterns/pjDataBase.db";
    private static boolean initialize = false;

    public static String getUrl() {
        if (!initialize) StartUp();
        return URL;
    }

    public static boolean isInitialized() {
        return initialize;
    }

    public static Connection getConnection() throws SQLException {
        if (!initialize) StartUp();
        return openConnection();
    }

    private static Connection openConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            conn.close();
            throw e;
        }
        return conn;
    }

    public static synchronized void StartUp() {
        if (initialize) return;
        ensureDirectoriesExist();
        try (Connection conn = openConnection()) {
            conn.setAutoCommit(false);
            try {
                // Keep existing data. Initialize only when the database has no user tables.
                if (!hasTable(conn, "USER") && !hasTable(conn, "CONFERENCE")
                        && !hasTable(conn, "CREDENTIALS") && !hasTable(conn, "REGISTRATION")) {
                    try {
                        executeSqlFile(conn, "schema.sql");
                    } catch (IOException | SQLException e) {
                        LOGGER.log(Level.WARNING, "schema.sql failed; using Java fallback", e);
                        conn.rollback();
                        createFallbackTables(conn);
                    }
                    try {
                        executeSeed(conn);
                    } catch (IOException | SQLException e) {
                        LOGGER.log(Level.WARNING, "seed.sql failed; using Java fallback data", e);
                        insertFallbackSeed(conn);
                    }
                } else {
                    createFallbackTables(conn);
                }
                conn.commit();
                initialize = true;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Database initialization failed", e);
            throw new IllegalStateException("Database initialization failed", e);
        }
    }

    private static void ensureDirectoriesExist() {
        File parent = new File(URL.substring("jdbc:sqlite:".length())).getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Cannot create database directory: " + parent);
        }
    }

    private static boolean hasTable(Connection conn, String name) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM sqlite_master WHERE type='table' AND lower(name)=lower(?)")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static String readSql(String filename) throws IOException {
        Path path = Path.of(filename);
        if (!Files.exists(path)) path = Path.of("src/main/resources", filename);
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private static void executeSqlFile(Connection conn, String filename) throws IOException, SQLException {
        String sql = readSql(filename).replaceAll("(?s)/\\*.*?\\*/", "");
        try (Statement stmt = conn.createStatement()) {
            for (String part : sql.split(";")) {
                if (!part.isBlank()) stmt.execute(part.trim());
            }
        }
    }

    private static void executeSeed(Connection conn) throws IOException, SQLException {
        String sql = readSql("seed.sql").replaceAll("(?s)/\\*.*?\\*/", "");
        try (Statement stmt = conn.createStatement()) {
            for (String part : sql.split(";")) {
                String statement = part.trim();
                if (statement.isEmpty()) continue;


                if (statement.matches("(?is)INSERT\\s+INTO\\s+USER\\s+VALUES\\s*\\(.*")) {
                    insertAdmin(conn);
                } else if (statement.matches("(?is)INSERT\\s+INTO\\s+CREDENTIALS\\s+VALUES\\s*\\(.*")) {
                } else {
                    stmt.execute(statement);
                }
            }
        }
    }

    private static void insertAdmin(Connection conn) throws SQLException {
        int userId;

        try (PreparedStatement find = conn.prepareStatement(
                "SELECT U_ID FROM CREDENTIALS WHERE Email = ?")) {
            find.setString(1, "admin@admin.ca");
            try (ResultSet rs = find.executeQuery()) {
                if (rs.next()) return;
            }
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO USER (FullName, Age, Role) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "ADMIN");
            ps.setInt(2, 1);
            ps.setString(3, "ADMIN");
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("Failed to obtain admin user ID");
                userId = keys.getInt(1);
            }
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO CREDENTIALS (U_ID, Email, Password) VALUES (?, ?, ?)")) {
            ps.setInt(1, userId);
            ps.setString(2, "admin@admin.ca");
            ps.setString(3, "4Dm!n");
            ps.executeUpdate();
        }
    }

    private static void insertFallbackSeed(Connection conn) throws SQLException {
        insertAdmin(conn);
    }

    private static void createFallbackTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS CONFERENCE (" +
                    "C_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Title TEXT NOT NULL, " +
                    "Capacity INTEGER NOT NULL, " +
                    "C_Date TEXT NOT NULL, " +
                    "Start_Time TEXT NOT NULL, " +
                    "End_Time TEXT NOT NULL, " +
                    "Location TEXT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS USER (" +
                    "U_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "FullName TEXT NOT NULL, " +
                    "Age INTEGER, " +
                    "Role TEXT CHECK(Role IN ('ADMIN','PARTICIPANT')))");

            stmt.execute("CREATE TABLE IF NOT EXISTS CREDENTIALS (" +
                    "Cred_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "U_ID INTEGER UNIQUE, " +
                    "Email TEXT UNIQUE NOT NULL, " +
                    "Password TEXT NOT NULL, " +
                    "FOREIGN KEY (U_ID) REFERENCES USER(U_ID) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS REGISTRATION (" +
                    "R_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "C_ID INTEGER, " +
                    "U_ID INTEGER, " +
                    "WaitingList INTEGER DEFAULT 0, " +
                    "Status TEXT CHECK(Status IN ('CONFIRMED','WAITLISTED')), " +
                    "WaitingListPosition INTEGER, " +
                    "FOREIGN KEY (C_ID) REFERENCES CONFERENCE(C_ID) ON DELETE CASCADE, " +
                    "FOREIGN KEY (U_ID) REFERENCES USER(U_ID) ON DELETE CASCADE, " +
                    "UNIQUE(C_ID,U_ID))");
        }
    }
}
