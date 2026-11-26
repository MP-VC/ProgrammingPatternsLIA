package vanier.com.programmingpatternslia.App.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;
import vanier.com.programmingpatternslia.App.Controllers.Locale.LocaleModifier;
import java.util.ResourceBundle;

public class SignUpController implements LocaleModifier {
    @FXML
    public Button Login;
    @FXML
    public PasswordField passwordfield;
    @FXML
    public PasswordField passcheckfield;
    @FXML
    public TextField agefield;
    @FXML
    public TextField mailfield;
    @FXML
    public TextField userfield;
    @FXML
    public ComboBox<String> languagecombo;

    @FXML
    public void initialize() {
        languagecombo.setItems(FXCollections.observableArrayList("English","French"));
        languagecombo.setOnAction(event -> switchLanguage((languagecombo.getValue())));
        updateLanguage();
    }
    public void updateLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle("vanier.com.programmingpatternslia.Internalization.file", GlobalLocale.getCurrentLocale());
        userfield.setPromptText(bundle.getString("textbox.fullname"));
        mailfield.setPromptText(bundle.getString("textbox.email"));
        agefield.setPromptText(bundle.getString("textbox.age"));
        passwordfield.setPromptText(bundle.getString("textbox.password"));
        passcheckfield.setPromptText(bundle.getString("textbox.passwordcheck"));
        Login.setText(bundle.getString("button.login"));
    }
}