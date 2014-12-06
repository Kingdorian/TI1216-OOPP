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
 * @author faris
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
    
    @FXML
    private void buttonHome(){
        currentMenuField.setText("Home");
        mainController.setCenterView("GameScreenHOME");
    }
    
    @FXML
    private void buttonTeam(){
        mainController.setCenterView("GameScreenTEAM");
        currentMenuField.setText("Team");
    }
    
    @FXML
    private void buttonSchedule(){
        mainController.setCenterView("GameScreenSCHEDULE");
        currentMenuField.setText("Schedule");
    }
    
    @FXML
    private void buttonRanking(){
        mainController.setCenterView("GameScreenRANKING");	
        currentMenuField.setText("Ranking");
    }
    
    @FXML
    private void buttonMarket(){
        mainController.setCenterView("GameScreenMARKET");	
        currentMenuField.setText("Market");
    }
    
    @FXML
    private void buttonOtherTeams(){
        mainController.setCenterView("GameScreenOTHERTEAMS");		
        currentMenuField.setText("Other Teams");
    }
}
