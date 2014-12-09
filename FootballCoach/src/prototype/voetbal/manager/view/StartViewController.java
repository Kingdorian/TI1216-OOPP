/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.voetbal.manager.view;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import prototype.voetbal.manager.VoetbalManager;

/**
 *
 * @author faris
 */
public class StartViewController implements ViewControllerInterface {
    
    private static VoetbalManager mainController;
    
    @FXML
    private void initialize(){
    }
    
    @Override
    public void setMainController(VoetbalManager mainController){
        this.mainController = mainController;
    }
    
    @FXML
    private void exitButton(){
        System.exit(0);
    }
    
    @FXML
    private void loadButton(){
        // Create a dialog
        Dialogs.create()
                .title("Not implemented")
                .masthead("This button is not implemented")
                .message("Please don't push this button again.")
                .showWarning();
    }
    
    @FXML
    private void nextWindow(){
        mainController.switchView("NextWindow");
    }
    
    @FXML
    private void newGame(){

    }
}
