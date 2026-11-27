package vanier.com.programmingpatternslia.App.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.swing.*;

public class ConfListController {
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


    /*@FXML
    public void changeWindow(MouseEvent event){
        Labeled clickedControl = (Labeled) event.getSource();
        String windowLabel = clickedControl.getText();

        switch(windowLabel){
            case "Home":
                break;
            case "Conferences":
                break;
            case "My Registrations":
                break;
            case "Waiting List":
                break;
            case "Profile":
                break;
        }
    }*/
}
