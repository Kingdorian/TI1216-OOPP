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
        setTextClub("Changed");
        setTextRanking("Changed");
        setTextNumberPlayers("Changed");
        setResults(1, 2, 3);
        setTeamLogo("http://eredivisie-images.s3.amazonaws.com/Eredivisie%20images/Eredivisie%20Badges/325/150x150.png");
    }

    @Override
    public void setMainController(Main mainController) {
        GameScreenHOMEController.mainController = mainController;
    }

    /**
     * Method to display the Club name in the GUI.
     */
    private void setTextClub(String clubName) {
        textClub.setText(clubName);
    }

    /**
     * Method to display the Ranking in the GUI.
     */
    private void setTextRanking(String ranking) {
        textRanking.setText(ranking);
    }

    /**
     * Method to display the Number of Players in the GUI.
     */
    private void setTextNumberPlayers(String noPlayers) {
        textNumberPlayers.setText(noPlayers);
    }

    /**
     * Method to display change the results (Won:, Drawn:, Lost:)
     */
    private void setResults(int won, int drawn, int lost) {
        textMatchesWon.setText(Integer.toString(won));
        textMatchesDrawn.setText(Integer.toString(drawn));
        textMatchesLost.setText(Integer.toString(lost));
    }

    /**
     * Method to display the team Logo
     *
     * @param url The url of the image to be displayed.
     */
    private void setTeamLogo(String url) {
        Image image = new Image(url, 200, 200, true, false);
        teamLogo.setImage(image);
    }

}
