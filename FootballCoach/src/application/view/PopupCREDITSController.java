/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.PopupControl;

/**
 *
 * @author Faris
 */
public class PopupCREDITSController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static PopupControl popupControl;

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
        isOkClicked = false;
    }

    @FXML
    private void buttonOK() {
        isOkClicked = true;
        popupControl.hide();
    }

    @FXML
    private void buttonCancel() {
        popupControl.hide();
    }

}
