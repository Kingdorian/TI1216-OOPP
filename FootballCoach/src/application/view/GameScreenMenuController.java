/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 *
 * @author Faris
 * @author Jochem
 */
public class GameScreenMenuController implements ViewControllerInterface {
    
    @FXML
    private Text currentMenuField;
    
    private static Main mainController;
    
    
    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize(){
    }
    
    @Override
    public void setMainController(Main mainController){
        GameScreenMenuController.mainController = mainController;
    }
  
    /**
     * Method that changes the centerview to GameScreenHOME when the given button is clicked.
     */
    @FXML
    private void buttonHome(){
        mainController.setCenterView("GameScreenHOME");
        currentMenuField.setText("Home"); 
    }
    
    /**
     * Method that changes the centerview to GameScreenTEAM when the given button is clicked.
     */  
    @FXML
    private void buttonTeam(){
        mainController.setCenterView("GameScreenTEAM");
        currentMenuField.setText("Team");
    }
    
    /**
     * Method that changes the centerview to GameScreenSCHEDULE when the given button is clicked.
     */ 
    @FXML
    private void buttonSchedule(){
        mainController.setCenterView("GameScreenSCHEDULE");
        currentMenuField.setText("Schedule");
    }
    
    /**
     * Method that changes the centerview to GameScreenRANKING when the given button is clicked.
     */ 
    @FXML
    private void buttonRanking(){
        mainController.setCenterView("GameScreenRANKING");	
        currentMenuField.setText("Ranking");
    }

    /**
     * Method that changes the centerview to GameScreenMARKET when the given button is clicked.
     */ 
    @FXML
    private void buttonMarket(){
        mainController.setCenterView("GameScreenMARKET");	
        currentMenuField.setText("Market");
    }
    
    /**
     * Method that changes the centerview to GameScreenOTHERTEAMS when the given button is clicked.
     */ 
    @FXML
    private void buttonOtherTeams(){
        mainController.setCenterView("GameScreenOTHERTEAMS");		
        currentMenuField.setText("Other Teams");
    }
}
