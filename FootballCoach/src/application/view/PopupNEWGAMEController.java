/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.controller.SaveGameHandler;
import application.model.Team;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextField;

/**
 * This is the controller class of the NEWGAME popup
 *
 * @author Faris
 */
public class PopupNEWGAMEController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static PopupControl popupControl;

    @FXML
    private ComboBox selectTeamBox;
    @FXML
    private ComboBox selectCompetitionBox;
    @FXML
    private TextField nameField;

    /**
     * Sets the stage (PopupControl) of this popup.
     *
     * @param popupControl the popups stage (PopupControl)
     */
    @Override
    public void setPopupStage(PopupControl popupControl) {
        this.popupControl = popupControl;
    }

    /**
     * Will return true if the OK button has been clicked, otherwise will return
     * false
     *
     * @return boolean: if the ok button has been clicked
     */
    @Override
    public boolean isOkClicked() {
        return isOkClicked;
    }

    /**
     * This code is executed when the view is loaded. It sets the main texts of
     * this popup.
     */
    @FXML
    private void initialize() {
        isOkClicked = false;

        // add all competitions that are available to the dropdown list.
        ArrayList<String> competitionNames = SaveGameHandler.getCompetitions();
        selectCompetitionBox.setItems(FXCollections.observableArrayList(competitionNames));
        selectTeamBox.setDisable(true);
    }

    /**
     * Executed when a new competition is selected, it updates the teamBox with
     * the teams in the newly selected competition.
     */
    @FXML
    private void changeTeamBox() {
        try {
            ArrayList<String> teamNames = new ArrayList<>();

            String compChoice = SaveGameHandler.getDefaultCompLoc() + selectCompetitionBox.getItems().get(selectCompetitionBox.getSelectionModel().getSelectedIndex()).toString();
            Team[] teams = SaveGameHandler.ldByCompByPath(compChoice + "/Teams.xml", compChoice + "/Matches.xml").getTeams();
            for (Team t : teams) {
                teamNames.add(t.getName());
            }

            selectTeamBox.setItems(FXCollections.observableArrayList(teamNames));
            if (!teamNames.isEmpty()) {
                selectTeamBox.setDisable(false);
                selectTeamBox.setValue(teamNames.get(0));
            }

        } catch (Exception e) {
        }
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will check if all input is correct and
     * close the popup
     */
    @FXML
    private void buttonOK() {
        if (nameField.getText().isEmpty()) {
        	Main.createModal("No Name", "The name field is empty", "Please write down your name in the text field.");
        	
        } else if (nameField.getText().length() > 10) {
        	Main.createModal("Name Too Long", "The name is too long", "Please enter a shorter name");
        	
        } else if (selectTeamBox.getSelectionModel().getSelectedIndex() == -1 && selectCompetitionBox.getSelectionModel().getSelectedIndex() == -1) {
        	Main.createModal("No Selection", "No team and competition selected", "Please select a team and competition in the dropdown list.");

        } else if (selectTeamBox.getSelectionModel().getSelectedIndex() == -1) {
        	Main.createModal("No Selection", "No team selected", "Please select a team in the dropdown list.");

        } else if (selectCompetitionBox.getSelectionModel().getSelectedIndex() == -1) {
        	Main.createModal("No Selection", "No competition selected", "Please select a competition in the dropdown list.");

        } else {
            // Set the chosen name and team in the Main class

            try {
                String compChoice = SaveGameHandler.getDefaultCompLoc() + selectCompetitionBox.getItems().get(selectCompetitionBox.getSelectionModel().getSelectedIndex()).toString();

                // Creating a new save and returning the competition to main.
                Main.setCompetition(SaveGameHandler.createNewSave(compChoice + "/Teams.xml", compChoice + "/Matches.xml"));
                Main.setChosenName(nameField.getText());
                Main.SetChosenTeamName(selectTeamBox.getItems().get(selectTeamBox.getSelectionModel().getSelectedIndex()).toString());
                Main.getCompetition().setChosenTeamName(Main.getChosenTeamName());
                Main.getCompetition().setName(Main.getChosenName());
                SaveGameHandler.saveGame(Main.getCompetition());

            } catch (FileNotFoundException e) {
                System.out.println("The required files could not be found.");
            } catch (IOException e) {
                System.out.println("IO Exception");
            }
            isOkClicked = true;
            popupControl.hide();
        }
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will close the popup
     */
    @FXML
    private void buttonCancel() {
        popupControl.hide();
    }

}
