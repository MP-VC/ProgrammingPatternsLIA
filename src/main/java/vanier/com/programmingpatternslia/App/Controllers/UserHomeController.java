package vanier.com.programmingpatternslia.App.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class UserHomeController {

    public Button viewProf;
    public Button viewWait;
    public Button viewReg;
    public Button viewConf;
    public Label welcomeText;
    public VBox sidebar;

    private void switchScene(ActionEvent event, String file) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/vanier/com/programmingpatternslia/UI/" + file
        ));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(loader.load());
    }


    @FXML
    public void onConferencesButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "ConfList.fxml");
    }

    @FXML
    public void onRegistrationButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "MyRegistrations.fxml");
    }

    @FXML
    public void onWaitListButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "WaitingList.fxml");
    }

    @FXML
    public void onProfileButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "Profile.fxml");
    }
}
