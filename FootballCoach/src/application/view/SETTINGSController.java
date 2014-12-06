/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author faris
 */
public class SETTINGSController implements PopupControllerInterface {

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
    private void initialze(){
        isOkClicked = false;
    }
    
    @FXML
    private void buttonOK(){
        isOkClicked = true;
        popupStage.close();
    }
    
    @FXML
    private void buttonCancel(){
        popupStage.close();
    }
    
    @FXML
    private void buttonError(){
        Dialogs.create()
                .title("This is the title")
                .masthead("this is an error message")
                .message("Invalid input:")
                .showError();
    }
    
    @FXML
    private void buttonWarning(){
        Dialogs.create()
                .title("This is the title")
                .masthead("this is a warning message")
                .message("The textfield above might contain content inappropriate for you age.\nRead at own risk!")
                .showWarning();
    }
    
    @FXML
    private void buttonInfo(){
        Dialogs.create()
                .title("This is the title")
                .masthead("this is an info message")
                .message("The textfield above contains the text: ")
                .showInformation();
    }
    
}
