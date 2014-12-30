/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import application.PlayAnimation;
import application.model.Match;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * This is the controller class of the PLAYMATCH screen of the main view of the
 * game screen.
 *
 * @author Faris
 */
public class GameScreenPLAYMATCHController implements ViewControllerInterface {

    private static Main mainController;

    @FXML
    Text score;
    @FXML
    Button playMatchButton;

    /**
     * This code is executed when the view is loaded. It sets the main texts of
     * this view.
     */
    @FXML
    private void initialize() {
    }

    /**
     * This gives this class a reference to the main class
     *
     * @param mainController the main class
     */
    @Override
    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }

    /**
     * This method is triggered when the play match button is clicked (event
     * handler assigned in the .fxml) and it triggers the match position
     * selection, generation and animation method in a different class to start.
     */
    @FXML
    private void playMatchButton() {
        playMatchButton.setDisable(true);

        Match result = PlayAnimation.playAnimation(null, null);
        score.setText(result.getPointsHomeTeam() + " - " + result.getPointsVisitorTeam());
    }
}
