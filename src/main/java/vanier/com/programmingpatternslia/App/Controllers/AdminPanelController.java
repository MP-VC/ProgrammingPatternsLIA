package vanier.com.programmingpatternslia.App.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminPanelController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
