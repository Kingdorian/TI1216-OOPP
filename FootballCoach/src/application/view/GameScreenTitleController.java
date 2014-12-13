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
        setTextBudget("Changed");
        setTextWelcome("Changed");
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

    /**
     * Method that sets the budget in the GUI
     *
     * @param budget	String representing the budget.
     */
    private void setTextBudget(String budget) {
        textBudget.setText(budget);

    }

    /**
     * Method that sets the budget in the GUI
     *
     * @param name	String representing the budget.
     */
    private void setTextWelcome(String name) {
        textWelcome.setText("Welcome, " + name);
    }

}
