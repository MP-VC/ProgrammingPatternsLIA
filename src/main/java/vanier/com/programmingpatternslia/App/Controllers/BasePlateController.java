package vanier.com.programmingpatternslia.App.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;
import vanier.com.programmingpatternslia.App.Controllers.Locale.LocaleModifier;

import java.util.ResourceBundle;

public class BasePlateController implements LocaleModifier {
    @FXML
    public Button home;
    @FXML
    public Button conferences;
    @FXML
    public Button myregistrations;
    @FXML
    public Button waitinglist;
    @FXML
    public Button profile;
    @FXML
    public Button admin;
    @FXML
    public void initialize() {
        updateLanguage();
    }

    @Override
    public void updateLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle("vanier.com.programmingpatternslia.Internalization.file", GlobalLocale.getCurrentLocale());
        home.setText(bundle.getString("button.home"));
        conferences.setText(bundle.getString("button.conferences"));
        myregistrations.setText(bundle.getString("button.myregistrations"));
        waitinglist.setText(bundle.getString("button.waitinglist"));
        profile.setText(bundle.getString("button.profile"));
        admin.setText(bundle.getString("button.admin"));
    }

}
