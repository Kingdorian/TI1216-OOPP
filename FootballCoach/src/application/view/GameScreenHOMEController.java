/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        textNumberPlayers.setText(Integer.toString(Main.getCompetition().getTeamByName(Main.getCompetition().getChosenTeamName()).getPlayers().size()));
        textMatchesWon.setText("Not implemented yet");
        textMatchesDrawn.setText("Not implemented yet");
        textMatchesLost.setText("Not implemented yet");
        
        try {
			java.io.FileInputStream imageLoader = new FileInputStream("XML/Savegames/" + Main.getCompetition().getSaveGameId() + "/images/" + Main.getChosenTeamName() + ".png");
	        Image image = new Image(imageLoader, 200, 200, true, false);
	        teamLogo.setImage(image);	
		} catch (FileNotFoundException e) {
			System.out.println("Image could not be found");
			e.printStackTrace();
		}
    }

    @Override
    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }
}
