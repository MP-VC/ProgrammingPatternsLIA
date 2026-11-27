package vanier.com.programmingpatternslia.App.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;

import java.util.ResourceBundle;

public class ProfileController extends BasePlateController {
    @FXML
    public Label myProfileLBL;
    @FXML
    public Label fnameLBL;
    @FXML
    public TextField fnameTXTBox;
    @FXML
    public Label emailLBL;
    @FXML
    public TextField emailTXTBox;
    @FXML
    public Label ageLBL;
    @FXML
    public TextField ageTXTBox;
    @FXML
    public Label passLBL;
    @FXML
    public PasswordField passField;
    @FXML
    public Button updateBTN;
    @FXML
    public Button backBTN;
    @Override
    public void updateLanguage() {
        super.updateLanguage();
        ResourceBundle bundle = ResourceBundle.getBundle("vanier.com.programmingpatternslia.Internalization.file", GlobalLocale.getCurrentLocale());
        myProfileLBL.setText(bundle.getString("label.myprofile"));
        fnameLBL.setText(bundle.getString("textbox.fullname"));
        emailLBL.setText(bundle.getString("textbox.email"));
        ageLBL.setText(bundle.getString("textbox.age"));
        passLBL.setText(bundle.getString("textbox.password"));
        updateBTN.setText(bundle.getString("button.update"));
        backBTN.setText(bundle.getString("button.back"));
    }
}
