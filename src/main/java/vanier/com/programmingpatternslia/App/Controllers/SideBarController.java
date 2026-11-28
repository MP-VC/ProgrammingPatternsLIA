package vanier.com.programmingpatternslia.App.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SideBarController {

    private void switchScene(ActionEvent event, String file) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/vanier/com/programmingpatternslia/UI/" + file
        ));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(loader.load());
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) throws IOException {
        switchScene(event, "UserHome.fxml");
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