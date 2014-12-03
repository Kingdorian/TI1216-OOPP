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
        currentMenuField.setText("Schedule");
        //ADD: CHANGE CENTER VIEW
    }
    
    @FXML
    private void buttonRanking(){
        currentMenuField.setText("Ranking");
        //ADD: CHANGE CENTER VIEW
    }
    
    @FXML
    private void buttonMarket(){
        currentMenuField.setText("Market");
        //ADD: CHANGE CENTER VIEW
    }
    
    @FXML
    private void buttonOtherTeams(){
        currentMenuField.setText("Other Teams");
        //ADD: CHANGE CENTER VIEW
    }
}
