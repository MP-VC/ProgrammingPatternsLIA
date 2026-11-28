package vanier.com.programmingpatternslia.App.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import vanier.com.programmingpatternslia.App.Controllers.CurrentUser.CurrentUser;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;

import java.util.ResourceBundle;

public class ProfileController extends BasePlateController {
    @FXML
    public Label myProfileLBL,fnameLBL,emailLBL,ageLBL,passLBL;
    @FXML
    public TextField fnameTXTBox,emailTXTBox,ageTXTBox;
    @FXML
    public PasswordField passField;
    @FXML
    public Button updateBTN,backBTN,lowBut,addBut,showPassField,updateInfo,backBut;
    @FXML
    public VBox sidebar;

    @FXML
    public void updateBTNOnClick() {

    }
    @FXML
    public void backBTNOnClick() {

    }
    @FXML
    public void lowBTNOnClick() {

    }
    @FXML
    public void addBTNOnClick() {

    }
    @FXML
    public void showPassFieldOnClick() {

    }
    @Override
    public void updateLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle(
                "vanier.com.programmingpatternslia.Internalization.file",
                GlobalLocale.getCurrentLocale()
        );
        myProfileLBL.setText(bundle.getString("label.myprofile"));
        fnameLBL.setText(bundle.getString("textbox.fullname"));
        emailLBL.setText(bundle.getString("textbox.email"));
        ageLBL.setText(bundle.getString("textbox.age"));
        passLBL.setText(bundle.getString("textbox.password"));
        if(CurrentUser.getCurrentUser() != null) {
            fnameTXTBox.setText(CurrentUser.getCurrentUser().getFullName());
            emailTXTBox.setText("");
            ageTXTBox.setText(String.valueOf(CurrentUser.getCurrentUser().getAge()));
        }
        if (updateBTN != null) updateBTN.setText(bundle.getString("button.update"));
        if (backBTN != null) backBTN.setText(bundle.getString("button.back"));
    }
}