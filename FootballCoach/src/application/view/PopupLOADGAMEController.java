/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import java.util.ArrayList;

import org.controlsfx.dialog.Dialogs;

import application.Main;
import application.controller.SaveGameHandler;
import javafx.collections.FXCollections;
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
        //Add items to the dropdown list
        ArrayList<Integer> saveGames = SaveGameHandler.getSaveGames();
        selectSaveGameBox.setItems(FXCollections.observableArrayList(saveGames));
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will close the popup
     */
    @FXML
    private void buttonOK() {
        if (selectSaveGameBox.getSelectionModel().getSelectedIndex() == -1) {
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No savegame selected")
                    .message("Please select a savegame in the dropdown list.")
                    .owner(Main.getOldPopup())
                    .showWarning();
        } else {
            int choice = Integer.parseInt(selectSaveGameBox.getItems().get(selectSaveGameBox.getSelectionModel().getSelectedIndex()).toString());
            try {
                Main.setCompetition(SaveGameHandler.loadCompetition(choice));
                Main.setChosenName(Main.getCompetition().getName());
                Main.SetChosenTeamName(Main.getCompetition().getChosenTeamName());

            } catch (Exception e) {
                // TODO Auto-generated catch block
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

}
