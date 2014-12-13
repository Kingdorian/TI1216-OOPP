/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Faris
 */
public class GameScreenHOMEController implements ViewControllerInterface {

    @FXML
    private Text textClub;
    @FXML
    private Text textRanking;
    @FXML
    private Text textNumberPlayers;
    @FXML
    private Text textMatchesLost;
    @FXML
    private Text textMatchesDrawn;
    @FXML
    private Text textMatchesWon;
    @FXML
    private ImageView teamLogo;

    private static Main mainController;

    /**
     * Code executed when the view is loaded.
     */
    @FXML
    private void initialize() {
        textClub.setText(Main.getChosenTeamName());
        textRanking.setText("Not implemented yet");
        textNumberPlayers.setText(Integer.toString(Main.getCompetition().getTeamByName(mainController.getChosenTeamName()).getPlayers().size()));
        textMatchesWon.setText("Not implemented yet");
        textMatchesDrawn.setText("Not implemented yet");
        textMatchesLost.setText("Not implemented yet");
        
        Image image = new Image("http://eredivisie-images.s3.amazonaws.com/Eredivisie%20images/Eredivisie%20Badges/325/150x150.png", 200, 200, true, false);
        teamLogo.setImage(image);
    }

    @Override
    public void setMainController(Main mainController) {
        GameScreenHOMEController.mainController = mainController;
    }
}
