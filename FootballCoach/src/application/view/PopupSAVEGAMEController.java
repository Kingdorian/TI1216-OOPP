/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import java.io.IOException;

import application.Main;
import application.controller.SaveGameHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PopupControl;
import javafx.scene.text.Text;

/**
 * This is the controller class of the SAVEGAME popup
 *
 * @author Faris
 * @author Jochem
 */
public class PopupSAVEGAMEController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static PopupControl popupControl;
    @FXML
    private Text saveGameID;

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
        saveGameID.setText(Integer.toString(Main.getCompetition().getSaveGameId()));
        isOkClicked = false;
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will close the popup
     */
    @FXML
    private void buttonOK() {
        try {
            isOkClicked = true;
            SaveGameHandler.saveGame(Main.getCompetition());
            popupControl.hide();
        } catch (IOException e) {
            System.out.println("Game could not be saved");
            e.printStackTrace();
        }
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will close the popup
     */
    @FXML
    private void buttonCancel() {
        isOkClicked = true;
        popupControl.hide();
    }

}
