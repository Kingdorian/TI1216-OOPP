/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.controller.SaveGameHandler;
import application.model.Competition;
import application.model.Team;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Faris
 */
public class PopupNEWGAMEController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static Stage popupStage;
    
    @FXML
    private ComboBox selectTeamBox;
    @FXML
    private ComboBox selectCompetitionBox;
    @FXML
    private TextField nameField;

    /**
     * Sets the stage of this dialog.
     *
     * @param main
     * @param popupStage
     */
    @Override
    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    @Override
    public boolean isOkClicked() {
        return isOkClicked;
    }

    @FXML
    private void initialize() {
        isOkClicked = false;
        
        // add items to the dropdown list
        ArrayList<String> teamNames = new ArrayList<>();
        Team[] teams = Main.getCompetition().getTeams();
        for(Team t : teams){
            if(!t.getName().equals(Main.getChosenTeamName()))
            teamNames.add(t.getName());
        }
        selectTeamBox.setItems(FXCollections.observableArrayList(teamNames));
        
        // WIP :: Add competition types.
        selectCompetitionBox.setItems(FXCollections.observableArrayList(new ArrayList<String>(){{ add("Not implemented yet"); add("Not implemented yet"); }}));
    }

    @FXML
    private void buttonOK() {
        if(nameField.getText().isEmpty())
            Dialogs.create()
                            .title("No Name")
                            .masthead("The name field is empty")
                            .message("Please write down your name in the text field.")
                            .showWarning();
        else if(selectTeamBox.getSelectionModel().getSelectedIndex() == -1 && selectCompetitionBox.getSelectionModel().getSelectedIndex() == -1)
            Dialogs.create()
                            .title("No Selection")
                            .masthead("No team and competition selected")
                            .message("Please select a team and competition in the dropdown list.")
                            .showWarning();
        else if(selectTeamBox.getSelectionModel().getSelectedIndex() == -1)
            Dialogs.create()
                            .title("No Selection")
                            .masthead("No team selected")
                            .message("Please select a team in the dropdown list.")
                            .showWarning();
        else if(selectCompetitionBox.getSelectionModel().getSelectedIndex() == -1)
            Dialogs.create()
                            .title("No Selection")
                            .masthead("No competition selected")
                            .message("Please select a competition in the dropdown list.")
                            .showWarning();
        else{
            // Set the chosen name and team in the Main class
        	
        	try {
        	// Creating a new save and returning the competition to main.
				Main.setCompetition(SaveGameHandler.createNewSave("FootballCoach/XML/Teams.xml", "FootballCoach/XML/Matches.xml"));
	            Main.setChosenName(nameField.getText());
	            Main.SetChosenTeamName(selectTeamBox.getItems().get(selectTeamBox.getSelectionModel().getSelectedIndex()).toString());	
			} catch (FileNotFoundException e) {
				System.out.println("The required files could not be found.");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IO Exception");
				e.printStackTrace();
			}
            isOkClicked = true;
            popupStage.close();
        }
    }

    @FXML
    private void buttonCancel() {
        popupStage.close();
    }

}
