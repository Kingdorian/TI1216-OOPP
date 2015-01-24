/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PopupControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * This is the controller class of a MODAL popup
 *
 * @author Jochem
 */
public class PopupMODALController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static PopupControl popupControl;
    @FXML
    private Text title;
    @FXML
    private Text warning;
    @FXML 
    private Text message;
    @FXML
    private ImageView symbol;

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
    	
    }
    
    /**
     * Method to set the values of the Modal window.
     * @param titlevalue		String value displayed as title
     * @param warningvalue		String value displayed as warning
     * @param messagevalue		String value displayed as message
     */
    public void setModalValues(String titlevalue, String warningvalue, String messagevalue) {
    	title.setText(titlevalue);
    	warning.setText(warningvalue);
    	message.setText(messagevalue);
        symbol.setImage(new Image(Main.class.getResourceAsStream("img/warning.png")));
    	
    }

    /**
     * This method is triggered when the OK button is clicked (event handler
     * assigned in the .fxml) and it will close the popup
     */
    @FXML
    private void buttonOK() {
            popupControl.hide();
    }

}
