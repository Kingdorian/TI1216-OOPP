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
 * This is the controller class of the Title screen of the upper bar of the game
 * screen.
 *
 * @author Faris
 * @author Jochem
 */
public class GameScreenTitleController implements ViewControllerInterface {

    @FXML
    private Text textBudget;
    @FXML
    private Text textWelcome;
    @FXML
    private Text textRound;

    private static Main mainController;

    /**
     * This code is executed when the view is loaded. It sets the main texts of
     * this view.
     */
    @FXML
    private void initialize() {
        // set this as the title controller class in the Main class
        Main.setTitleController(this);

        // add texts
        refreshMoney();
        refreshRound();
        textWelcome.setText("Welcome, " + Main.getChosenName());
    }

    /**
     * This gives this class a reference to the main class
     *
     * @param mainController the main class
     */
    @Override
    public void setMainController(Main mainController) {
        GameScreenTitleController.mainController = mainController;
    }

    /**
     * Method that clears the rootlayout and changes the centerview to mainmenu
     * (return to mainmenu..)
     */
    @FXML
    private void buttonTitleScreen() {
        mainController.cleanRootLayout();
        mainController.setCenterView("MainMenu");
    }

    /**
     * Method that opens the settings popup.
     */
    @FXML
    private void buttonSettings() {
        mainController.createPopup("PopupSETTINGS", "Settings");
    }

    /**
     * Method that opens the save game popup.
     */
    @FXML
    private void buttonSaveGame() {
        mainController.createPopup("PopupSAVEGAME", "Save Game");
    }

    /**
     * Method that opens the play match screen.
     */
    @FXML
    private void buttonNextMatch() {
//        if (!Main.getMenuController().getCurrentMenuField().getText().equals("Play Match")) {
        	Main.getCompetition().playNextRound();
            Main.getMenuController().getCurrentMenuField().setText("Play Match");
            mainController.setCenterView("GameScreenPLAYMATCH");
//        }
    }

    /**
     * Method that refreshes the amount of money being displayed
     */
    public void refreshMoney() {
        textBudget.setText("$ " + Main.getCompetition().getTeamByName(Main.getChosenTeamName()).getBudget());
    }

    /**
     * Method that refreshes the current round number being displayed
     */
    public void refreshRound() {
        int round = Main.getCompetition().getRound();
        if (round != -1) {
            textRound.setText("ROUND " + round);
        } else {
            textRound.setText("COMPLETED");
        }
    }
}
