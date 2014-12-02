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
 * @author faris
 * @author Jochem
 */
public class MainMenuController implements ViewControllerInterface {
    
    private static Main mainController;
    
    //Load FXML elements to edit in code.
    @FXML
    private ImageView logo;
    
    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize(){
        // Logo start animation
    	FadeTransition fade = new FadeTransition(Duration.millis(6000), logo);
    	fade.setFromValue(0);
    	fade.setToValue(1);
    	fade.setCycleCount(1);
    	fade.play();
    	TranslateTransition translate = new TranslateTransition(Duration.millis(6000), logo);
        translate.setFromY(-50);
        translate.setToY(0);
        translate.setCycleCount(1);
        translate.play();
        
    }
    
    @Override
    public void setMainController(Main mainController){
        MainMenuController.mainController = mainController;
    }
    
    /**
     * Method to switch to the "test" view.
     */
    @FXML
    private void button(){
        mainController.switchView("GameScreen");
    }
   
}
