package vanier.com.programmingpatternslia.DataBase.Credential;

import vanier.com.programmingpatternslia.DataBase.DataBaseCore;
import vanier.com.programmingpatternslia.Error.CredentialError;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import javax.crypto.*;

public class CredentialDAO {
    private static String path = DataBaseCore.getUrl();
    public void createPassword(String fullName, int u_ID, String email, String password) throws Exception {
        if(password.length() < 6) throw new CredentialError("Password too short");
        String sql = "INSERT INTO CREDENTIALS (FullName, U_ID, Email, Password) VALUES (?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            password = makeEncryptedPassword(password);

            pstmt.setString(1, fullName);
            pstmt.setInt(2, u_ID);
            pstmt.setString(3, email);
            pstmt.setString(4, password);

            pstmt.executeUpdate();
        }
    }
    public void updatePassword(String email, String password) throws Exception {
        if(password.length() < 6) throw new CredentialError("Password too short");
        String sql = "UPDATE CREDENTIALS SET Password = ? WHERE Email = ?";
        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            password = makeEncryptedPassword(password);
            pstmt.setString(1, password);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        }
    }
    private String makeEncryptedPassword(String password) throws Exception {
        // Generating objects of KeyGenerator & SecretKey
        KeyGenerator keygenerator
                = KeyGenerator.getInstance("DES");
        SecretKey myDesKey = keygenerator.generateKey();

        // Creating object of Cipher
        Cipher desCipher;
        desCipher = Cipher.getInstance("DES");

        // Encrypting text
        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
        return Arrays.toString(desCipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));
    }
    private String getDecryptedPassword(String password) throws Exception {
        KeyGenerator keygenerator
                = KeyGenerator.getInstance("DES");
        SecretKey myDesKey = keygenerator.generateKey();

        // Creating object of Cipher
        Cipher desCipher;
        desCipher = Cipher.getInstance("DES");
        // Decrypting text
        desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
        return Arrays.toString(desCipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));
    }
}
