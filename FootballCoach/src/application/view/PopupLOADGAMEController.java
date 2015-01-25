/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import application.controller.SaveGameHandler;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PopupControl;

/**
 * This is the controller class of the LOADGAME popup
 *
 * @author Faris
 * @author Jochem
 */
public class PopupLOADGAMEController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static PopupControl popupControl;
    @FXML
    private ComboBox selectSaveGameBox;

    /**
     * This event closes the game.
     */
    private final EventHandler deleteSave = (EventHandler<Event>) (Event e) -> {
        String choice = selectSaveGameBox.getItems().get(selectSaveGameBox.getSelectionModel().getSelectedIndex()).toString();
        // Delete the savegame if OK is pressed.
        SaveGameHandler.deleteSaveGame(Integer.parseInt(choice.split("\\.")[0]));
        // Reload the dropdown list
        initialize();
    };

    
    /**
     * Sets the stage (PopupControl) of this popup. HEAD
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
        //Add items to the dropdown list
        ArrayList<String> saveGames = new ArrayList<>();
        for (int i = 0; i < SaveGameHandler.getSaveGames().size(); i++) {
            String[] dateParts;
            try {
                dateParts = SaveGameHandler.getDateById(SaveGameHandler.getSaveGames().get(i)).toString().split(" ");
                String team = SaveGameHandler.getTeamNameById(SaveGameHandler.getSaveGames().get(i));
                String name = SaveGameHandler.getNameById(SaveGameHandler.getSaveGames().get(i));
                saveGames.add(SaveGameHandler.getSaveGames().get(i) + ". " + dateParts[0] + " " + dateParts[1] + " " + dateParts[2] + " " + team + " By: " + name);
            } catch (FileNotFoundException e) {
            }
        }
        Collections.sort(saveGames, (String s1, String s2) -> {
            return Integer.parseInt(s1.split("\\.")[0]) - Integer.parseInt(s2.split("\\.")[0]);
        });
        selectSaveGameBox.setItems(FXCollections.observableArrayList(saveGames));
        if (!saveGames.isEmpty()) {
            selectSaveGameBox.setValue(saveGames.get(0));
        }
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will close the popup
     */
    @FXML
    private void buttonOK() {
        if (selectSaveGameBox.getSelectionModel().getSelectedIndex() == -1) {
            Main.createModal("No Selection", "No savegame selected", "Please select a savegame in the dropdown list.");
        } else {
            int choice = Integer.parseInt(selectSaveGameBox.getItems().get(selectSaveGameBox.getSelectionModel().getSelectedIndex()).toString().split("\\.")[0]);
            try {
                Main.setCompetition(SaveGameHandler.loadCompetition(choice));
                System.out.println(Main.getCompetition().getName());
                Main.setChosenName(Main.getCompetition().getName());
                Main.SetChosenTeamName(Main.getCompetition().getChosenTeamName());

            } catch (Exception e) {
            	e.printStackTrace();
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

    /**
     * This method is triggered when the delete button is clicked. It will
     * delete the selected savegame permanently after asking the user for
     * confirmation.
     */
    @FXML
    private void buttonDelete() {
        if (selectSaveGameBox.getSelectionModel().getSelectedIndex() == -1) {
        	Main.createModal("No Selection", "No savegame selected", "Please select a savegame in the dropdown list.");
        	
        } else {
            try {
                // Ask the user is they want to delete the savegame.
                Main.createModal("Savegame", "Are you sure want to permanently delete the selected savegame?", "Clicking Ok will permanently delete the selected savegame", deleteSave);               	
            } catch (Exception e) {
            	
            }
        }
    }

}
