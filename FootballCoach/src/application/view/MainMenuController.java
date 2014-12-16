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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Faris
 * @author Jochem
 */
public class MainMenuController implements ViewControllerInterface {

    private static Main mainController;

    //Load FXML elements to edit in code.
    @FXML
    private ImageView logo;
    @FXML
    private Button buttonContinue;

    /**
     * Code executed when the view is loaded.
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
        if(Main.getChosenTeamName().equals(""))
            buttonContinue.setDisable(true);
    }

    @Override
    public void setMainController(Main mainController) {
        MainMenuController.mainController = mainController;
    }

    /**
     * Method to switch to the game's HOME screen.
     */
    @FXML
    private void buttonContinue() {
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
        mainController.createPopup("PopupSETTINGS", "Settings", "/application/img/settings.png");
    }

    /**
     * Method that opens the new game popup.
     */
    @FXML
    private void buttonNewGame() {
        if(mainController.createPopup("PopupNEWGAME", "New Game", "/application/img/icon.png"))
            buttonContinue.setDisable(false);
    }

    /**
     * Method that opens the load game popup.
     */
    @FXML
    private void buttonLoadGame() {
        mainController.createPopup("PopupLOADGAME", "Load Game", "/application/img/icon.png");
    }

    /**
     * Method that opens the credits popup.
     */
    @FXML
    private void buttonCredits() {
        mainController.createPopup("PopupCREDITS", "Credits", "/application/img/icon.png");
    }
}
