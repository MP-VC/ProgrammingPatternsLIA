package vanier.com.programmingpatternslia.App.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ConfManagementController {
    @FXML
    public Button viewConfButton,editConfButton,deleteConfButton;

    @FXML
    private TableView conferencesTableView;

    @FXML
    private TableColumn titleCol, dateCol, startTimeCol, endTimeCol, capacityCol, availableSeatsCol, locationCol;

    @FXML
    private TextField searchConfTextField;

    @FXML
    private Label confyRenceLabel,homeLabel, confLabel, myRegistrationsLabel, waitingListLabel, profileLabel;

    @FXML
    public void searchConferences(ActionEvent event){

    }

    @FXML
    public void viewConferenceDetails(ActionEvent event){

    }

    @FXML
    public void editConference(ActionEvent event){

    }

    @FXML
    public void deleteConference(ActionEvent event){

    }
}
