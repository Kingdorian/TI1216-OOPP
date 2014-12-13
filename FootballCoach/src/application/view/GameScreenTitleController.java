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
public class GameScreenTitleController implements ViewControllerInterface {

    @FXML
    private Text textBudget;
    @FXML
    private Text textWelcome;

    private static Main mainController;

    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize() {
        textBudget.setText("€ " + "Not implemented yet");
        textWelcome.setText("Welcome, " + Main.getChosenName());
    }

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
        mainController.createPopup("PopupSETTINGS", "Settings", "/application/img/icon.png");
    }
}
