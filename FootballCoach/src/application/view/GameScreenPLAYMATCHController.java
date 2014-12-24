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
 *
 * @author faris
 */
public class GameScreenPLAYMATCHController implements ViewControllerInterface {
    
    private static Main mainController;
    
    @FXML
    Text score;
    @FXML
    Button playMatchButton;
    
    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize() {
    }

    @Override
    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    private void playMatchButton(){
//        Match result = GenerateMatch.generateMatch(Main.getCompetition().getTeamByName(Main.getChosenTeamName()), Main.getCompetition().getTeamByName("Feyenoord"));
//        score.setText(result.getPointsHomeTeam() + " - " + result.getPointsVisitorTeam());
//        System.out.println("Play match is not correctly implemented yet.");
        playMatchButton.setDisable(true);
        
        Match result = PlayAnimation.playAnimation(null, null, null, null, null, null);
        score.setText(result.getPointsHomeTeam() + " - " + result.getPointsVisitorTeam());
    }
}
