/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 *
 * @author Faris
 */
public class PopupCREDITSController implements PopupControllerInterface {

    private boolean isOkClicked = false;
    private static Stage popupStage;

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
        isOkClicked = false;
    }

    @FXML
    private void buttonOK() {
        isOkClicked = true;
        popupStage.close();
    }

    @FXML
    private void buttonCancel() {
        popupStage.close();
    }

}
