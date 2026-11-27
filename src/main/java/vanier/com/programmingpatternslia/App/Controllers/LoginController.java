package vanier.com.programmingpatternslia.App.Controllers;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;
import vanier.com.programmingpatternslia.App.Controllers.Locale.LocaleModifier;
import java.util.ResourceBundle;
public class LoginController implements LocaleModifier {
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
        languagecombo.setItems(FXCollections.observableArrayList("English","French"));
        languagecombo.setOnAction(event -> switchLanguage((languagecombo.getValue())));
        updateLanguage();
    }
    @FXML
    public void loginButtonOnClick() {
        String username = userfield.getText();
        String password = passfield.getText();
    }
    @Override
    public void updateLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle("vanier.com.programmingpatternslia.Internalization.file", GlobalLocale.getCurrentLocale());
        userfield.setPromptText(bundle.getString("textbox.fullname"));
        passfield.setPromptText(bundle.getString("textbox.password"));
        Login.setText(bundle.getString("button.login"));
    }
}
