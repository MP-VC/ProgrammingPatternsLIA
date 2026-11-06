package vanier.com.programmingpatternslia.DataBase.Credential;

import vanier.com.programmingpatternslia.DataBase.DataBaseCore;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import javax.crypto.*;

public class CredentialDAO {
    private static String path = DataBaseCore.getUrl();
    public void createPassword(String fullName, int u_ID, String email, String password) throws SQLException {
        String sql = "INSERT INTO CREDENTIALS (FullName, U_ID, Email, Password) VALUES (?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(path);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Generating objects of KeyGenerator & SecretKey
            KeyGenerator keygenerator
                    = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();

            // Creating object of Cipher
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");

            // Encrypting text
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            String textEncrypted = Arrays.toString(desCipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));

            pstmt.setString(1, fullName);
            pstmt.setInt(2, u_ID);
            pstmt.setString(3, email);
            pstmt.setString(4, textEncrypted);

            pstmt.executeUpdate();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getDecryptedPassword(String password) throws Exception {
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
