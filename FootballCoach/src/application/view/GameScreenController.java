/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Jochem
 */
public class GameScreenController implements ViewControllerInterface {
    
    private static Main mainController;
    
    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize(){
        
    }
    
    @Override
    public void setMainController(Main mainController){
        GameScreenController.mainController = mainController;
    }
    
    /**
     * Method to change to MainMenu when the given button is clicked.
     */
    @FXML
    private void buttonTitleScreen(){
        mainController.switchView("MainMenu");
    }
   
}
