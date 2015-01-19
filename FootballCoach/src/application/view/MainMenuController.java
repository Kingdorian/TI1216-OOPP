/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.model.Players;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * This is the controller class of the main menu screen of the startup screen.
 *
 * @author Faris
 * @author Jochem
 */
public class MainMenuController implements ViewControllerInterface {

    private static Main mainController;

    @FXML
    private ImageView logo;
    @FXML
    private Button buttonContinue;

    /**
     * This event sets the continue button to active and triggers it
     */
    private final EventHandler triggerContinue = new EventHandler<Event>() {
        @Override
        public void handle(Event e) {
            buttonContinue.setDisable(false);
            buttonContinue();
        }
    };

    /**
     * This code is executed when the view is loaded. It sets the main texts of
     * this view.
     */
    @FXML
    private void initialize() {
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

        // Disable continue button if the game hasn't started yet
        if (Main.getCompetition() == null) {
            buttonContinue.setDisable(true);
        }
    }

    /**
     * This gives this class a reference to the main class
     *
     * @param mainController the main class
     */
    @Override
    public void setMainController(Main mainController) {
        MainMenuController.mainController = mainController;
    }

    /**
     * Method to switch to the game's HOME screen.
     */
    @FXML
    private void buttonContinue() {
        // Market test
//        for (Players pl : Main.getCompetition().getTeamByName("Feyenoord").getPlayers()) {
//            Main.getCompetition().getMarket().addPlayer(pl, 5);
//        }

        //Testing budget.
//        Main.getCompetition().getTeamByName(Main.getChosenTeamName()).setBudget(5000000);

        mainController.setCenterView("GameScreenHOME");
        mainController.setLeftView("GameScreenMenu");
        mainController.setTopView("GameScreenTitle");
    }

    /**
     * Method to close the application.
     */
    @FXML
    private void buttonExit() {
        // show pop-up: are you sure you want to quit? if ok is clicked: quit game, else don't quit game
        Action response = Dialogs.create()
                .title("Quit")
                .masthead("Are you sure you want to quit the application?")
                .actions(Dialog.Actions.OK, Dialog.Actions.CANCEL)
                .owner(Main.getOldPopup())
                .showConfirm();

        if (response == Dialog.Actions.OK) {
            System.exit(0);
        } else {
            // User cancels and returns to the application
        }
    }

    /**
     * Method that opens the settings popup.
     */
    @FXML
    private void buttonSettings() {
        mainController.createPopup("PopupSETTINGS", "Settings");
    }

    /**
     * Method that opens the new game popup.
     */
    @FXML
    private void buttonNewGame() {
        mainController.createPopup("PopupNEWGAME", "New Game", triggerContinue);

    }

    /**
     * Method that opens the load game popup.
     */
    @FXML
    private void buttonLoadGame() {
        mainController.createPopup("PopupLOADGAME", "Load Game", triggerContinue);
    }

    /**
     * Method that opens the credits popup.
     */
    @FXML
    private void buttonCredits() {
        mainController.createPopup("PopupCREDITS", "Credits");
    }

}
