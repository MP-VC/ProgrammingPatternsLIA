package vanier.com.programmingpatternslia.App.Controllers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;
import vanier.com.programmingpatternslia.App.Controllers.Locale.LocaleModifier;
import vanier.com.programmingpatternslia.DataBase.Credential.CredentialDAO;

import java.sql.SQLException;
import java.util.ResourceBundle;
public class LoginController implements LocaleModifier {
    CredentialDAO credentials = CredentialDAO.getInstance();
    @FXML
    public TextField userfield;
    @FXML
    public PasswordField passfield;
    @FXML
    public Button Login;
    @FXML
    public ComboBox<String> languagecombo;
    @FXML
    public void initialize() {
        languagecombo.setItems(FXCollections.observableArrayList(GlobalLocale.getLanguages().keySet()));
        for (String language : GlobalLocale.getLanguages().keySet())
            if (GlobalLocale.getLanguages().get(language).getLanguage().equals(GlobalLocale.getCurrentLocale().getLanguage())) {
                languagecombo.setValue(language);
                break;
            }
        updateLanguage();
    }
    @FXML
    public void loginButtonOnClick() throws SQLException {
        String email = userfield.getText();
        String password = passfield.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(email.isEmpty() || password.isEmpty()) {
            alert.setHeaderText(null);
            alert.setContentText("Please enter your username and password");
            alert.showAndWait();
            return;
        }
        if(credentials.verifyPassword(email,password)) {
            //todo
        }
        else {
            alert.setHeaderText(null);
            alert.setContentText("Invalid Credentials");
            alert.showAndWait();
        }
    }
    @Override
    public void updateLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle("vanier.com.programmingpatternslia.Internalization.file", GlobalLocale.getCurrentLocale());
        userfield.setPromptText(bundle.getString("textbox.email"));
        passfield.setPromptText(bundle.getString("textbox.password"));
        Login.setText(bundle.getString("button.login"));
    }
}
