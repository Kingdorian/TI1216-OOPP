/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 *
 * @author Faris
 * @author Jochem
 */
public class GameScreenMenuController implements ViewControllerInterface {

    @FXML
    private Text currentMenuField;
    @FXML
    private Button buttonHome;

    private static Main mainController;

    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize() {
    	buttonHome.setText(Main.getChosenTeamName());
    	currentMenuField.setText(Main.getChosenTeamName());
        Main.setMenuController(this);
    }

    @Override
    public void setMainController(Main mainController) {
        GameScreenMenuController.mainController = mainController;
    }

    /**
     * Method that changes the centerview to GameScreenHOME when the given
     * button is clicked.
     */
    @FXML
    private void buttonHome() {
        if(!currentMenuField.getText().equals(Main.getChosenTeamName())){
            currentMenuField.setText(Main.getChosenTeamName());
            mainController.setCenterView("GameScreenHOME");
        }
    }

    /**
     * Method that changes the centerview to GameScreenTEAM when the given
     * button is clicked.
     */
    @FXML
    private void buttonTeam() {
        if(!currentMenuField.getText().equals("My Team")){
            currentMenuField.setText("My Team");
            mainController.setCenterView("GameScreenTEAM");
        }
    }

    /**
     * Method that changes the centerview to GameScreenSCHEDULE when the given
     * button is clicked.
     */
    @FXML
    private void buttonSchedule() {
        if(!currentMenuField.getText().equals("Schedule")){
            currentMenuField.setText("Schedule");
            mainController.setCenterView("GameScreenSCHEDULE");
        }
    }

    /**
     * Method that changes the centerview to GameScreenRANKING when the given
     * button is clicked.
     */
    @FXML
    private void buttonRanking() {
        if(!currentMenuField.getText().equals("Ranking")){
            currentMenuField.setText("Ranking");
            mainController.setCenterView("GameScreenRANKING");
        }
    }

    /**
     * Method that changes the centerview to GameScreenMARKET when the given
     * button is clicked.
     */
    @FXML
    private void buttonMarket() {
        if(!currentMenuField.getText().equals("Market")){
            currentMenuField.setText("Market");
            mainController.setCenterView("GameScreenMARKET");
        }
    }

    /**
     * Method that changes the centerview to GameScreenOTHERTEAMS when the given
     * button is clicked.
     */
    @FXML
    private void buttonOtherTeams() {
        if(!currentMenuField.getText().equals("Other Teams")){
            currentMenuField.setText("Other Teams");
            mainController.setCenterView("GameScreenOTHERTEAMS");
        }
    }

    public Text getCurrentMenuField() {
        return currentMenuField;
    }

    public void setCurrentMenuField(Text currentMenuField) {
        this.currentMenuField = currentMenuField;
    }

}
