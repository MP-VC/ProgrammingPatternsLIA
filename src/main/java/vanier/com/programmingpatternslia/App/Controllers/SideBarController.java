package vanier.com.programmingpatternslia.App.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;
import vanier.com.programmingpatternslia.App.Controllers.Locale.LocaleModifier;

import java.io.IOException;
import java.util.ResourceBundle;

public class SideBarController implements LocaleModifier {
    @FXML
    public Button profSide,regSide,confSide,waitSide,homeSide;
    @FXML
    public void initialize() {updateLanguage();}
    private void switchScene(ActionEvent event, String file) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/vanier/com/programmingpatternslia/UI/" + file
        ));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(loader.load());
    }
    @FXML
    public void onHomeButtonClick(ActionEvent event) throws IOException { switchScene(event, "UserHome.fxml"); }
    @FXML
    public void onConferencesButtonClick(ActionEvent event) throws IOException { switchScene(event, "ConfList.fxml"); }
    @FXML
    public void onRegistrationButtonClick(ActionEvent event) throws IOException { switchScene(event, "MyRegistrations.fxml"); }
    @FXML
    public void onWaitListButtonClick(ActionEvent event) throws IOException { switchScene(event, "WaitingList.fxml"); }
    @FXML
    public void onProfileButtonClick(ActionEvent event) throws IOException { switchScene(event, "Profile.fxml"); }
    @Override
    public void updateLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle("vanier.com.programmingpatternslia.Internalization.file",GlobalLocale.getCurrentLocale());
        profSide.setText(bundle.getString("button.profile"));
        regSide.setText(bundle.getString("button.myregistrations"));
        confSide.setText(bundle.getString("button.conferences"));
        waitSide.setText(bundle.getString("button.waitinglist"));
        homeSide.setText(bundle.getString("button.home"));
    }
}