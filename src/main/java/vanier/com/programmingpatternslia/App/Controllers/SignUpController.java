package vanier.com.programmingpatternslia.App.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;
import java.util.ResourceBundle;
public class SignUpController extends LoginController {
    @FXML
    public PasswordField passcheckfield;
    @FXML
    public TextField agefield;
    @FXML
    public TextField mailfield;

    @FXML
    @Override
    public void loginButtonOnClick() {
        String passcheck = passcheckfield.getText();
        String age = agefield.getText();
    }
    @Override
    public void updateLanguage() {
        super.updateLanguage();
        ResourceBundle bundle = ResourceBundle.getBundle("vanier.com.programmingpatternslia.Internalization.file", GlobalLocale.getCurrentLocale());
        mailfield.setPromptText(bundle.getString("textbox.email"));
        agefield.setPromptText(bundle.getString("textbox.age"));
        passcheckfield.setPromptText(bundle.getString("textbox.passwordcheck"));
        Login.setText(bundle.getString("button.signup"));
    }
}