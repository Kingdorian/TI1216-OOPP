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
 * This is the controller class of the Menu bar of the left side bar of the game
 * screen.
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
     * This code is executed when the view is loaded. It sets the main texts of
     * this view.
     */
    @FXML
    private void initialize() {
        buttonHome.setText(Main.getChosenTeamName());
        currentMenuField.setText(Main.getChosenTeamName());
        Main.setMenuController(this);
    }

    /**
     * This gives this class a reference to the main class
     *
     * @param mainController the main class
     */
    @Override
    public void setMainController(Main mainController) {
        GameScreenMenuController.mainController = mainController;
    }

    /**
     * Method that changes the centerview to GameScreenHOME when the button is
     * clicked.
     */
    @FXML
    private void buttonHome() {
        if (!currentMenuField.getText().equals(Main.getChosenTeamName())) {
            currentMenuField.setText(Main.getChosenTeamName());
            mainController.setCenterView("GameScreenHOME");
        }
    }

    /**
     * Method that changes the centerview to GameScreenTEAM when the button is
     * clicked.
     */
    @FXML
    private void buttonTeam() {
        if (!currentMenuField.getText().equals("My Team")) {
            currentMenuField.setText("My Team");
            mainController.setCenterView("GameScreenTEAM");
        }
    }

    /**
     * Method that changes the centerview to GameScreenSCHEDULE when the button
     * is clicked.
     */
    @FXML
    private void buttonSchedule() {
        if (!currentMenuField.getText().equals("Schedule")) {
            currentMenuField.setText("Schedule");
            mainController.setCenterView("GameScreenSCHEDULE");
        }
    }

    /**
     * Method that changes the centerview to GameScreenRANKING when the button
     * is clicked.
     */
    @FXML
    private void buttonRanking() {
        if (!currentMenuField.getText().equals("Ranking")) {
            currentMenuField.setText("Ranking");
            mainController.setCenterView("GameScreenRANKING");
        }
    }

    /**
     * Method that changes the centerview to GameScreenMARKET when the button is
     * clicked.
     */
    @FXML
    private void buttonMarket() {
        if (!currentMenuField.getText().equals("Market")) {
            currentMenuField.setText("Market");
            mainController.setCenterView("GameScreenMARKET");
        }
    }

    /**
     * Method that changes the centerview to GameScreenOTHERTEAMS when the
     * button is clicked.
     */
    @FXML
    private void buttonOtherTeams() {
        if (!currentMenuField.getText().equals("Other Teams")) {
            currentMenuField.setText("Other Teams");
            mainController.setCenterView("GameScreenOTHERTEAMS");
        }
    }

    /**
     * Get the name of the screen currently being displayed in the main view of
     * the game screen.
     *
     * @return name of the current main menu view
     */
    public Text getCurrentMenuField() {
        return currentMenuField;
    }

    /**
     * Set the name of the screen currently being displayed in the main view of
     * the gaem screen.
     *
     * @param currentMenuField name of the current main menu view
     */
    public void setCurrentMenuField(Text currentMenuField) {
        this.currentMenuField = currentMenuField;
    }

}
