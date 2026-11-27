package vanier.com.programmingpatternslia.App.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.swing.*;

public class ConfManagementController {
    @FXML
    private TableView conferencesTableView;

    @FXML
    private TableColumn titleCol, dateCol, startTimeCol, endTimeCol, capacityCol, availableSeatsCol, locationCol;

    @FXML
    private TextField searchConfTextField;

    @FXML
    private Label homeLabel, confLabel, myRegistrationsLabel, waitingListLabel, profileLabel;

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
