/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import java.io.IOException;
import java.util.ArrayList;

import org.controlsfx.dialog.Dialogs;

import application.Main;
import application.controller.SaveGameHandler;
import application.model.Team;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Jochem
 */
public class PopupSAVEGAMEController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static Stage popupStage;
    @FXML
    private Text saveGameID;

    /**
     * Sets the stage of this dialog.
     *
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
    	saveGameID.setText(Integer.toString(Main.getCompetition().getSaveGameId()));
        isOkClicked = false;
    }

    @FXML
    private void buttonOK() {
    	try {
            isOkClicked = true; 		
			SaveGameHandler.saveGame(Main.getCompetition());
	        popupStage.close();			
		} catch (IOException e) {
			System.out.println("Game could not be saved");
			e.printStackTrace();
		}
    }
    
    @FXML
    private void buttonCancel() {
        isOkClicked = true;
        popupStage.close();
    }

}
