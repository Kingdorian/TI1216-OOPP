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
 *
 * @author Jochem
 */
public class PopupSAVEGAMEController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static PopupControl popupControl;
    @FXML
    private Text saveGameID;

    /**
     * Sets the stage of this dialog.
     *
     * @param popupStage
     */
    @Override
    public void setPopupStage(PopupControl popupControl) {
        this.popupControl = popupControl;
    }

    @Override
    public boolean isOkClicked() {
        return isOkClicked;
    }

    @FXML
    private void initialize() {
    	saveGameID.setText(Integer.toString(Main.getCompetition().getSaveGameId()));
        isOkClicked = false;
    }

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
    
    @FXML
    private void buttonCancel() {
        isOkClicked = true;
        popupControl.hide();
    }

}
