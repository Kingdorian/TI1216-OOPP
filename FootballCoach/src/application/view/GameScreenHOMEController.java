/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.Main;
import application.model.Competition;
import application.model.Team;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * This is the controller class of the HOME screen of the main view of the game
 * screen.
 *
 * @author Faris
 * @author Jochem
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
     * This code is executed when the view is loaded. It sets the main texts of
     * this view.
     */
    @FXML
    private void initialize() {
        String clubName = Main.getChosenTeamName(); 
        Competition competition = Main.getCompetition();
        Team team = competition.getTeamByName(clubName);
                
        textClub.setText(clubName);
        textRanking.setText(Integer.toString(competition.getRank(team)));
        textNumberPlayers.setText(Integer.toString(team.getPlayers().size()));

        // Get an array with the matches won, drawn, lost and the amout of goals
        textMatchesWon.setText(Integer.toString(team.getWins()));
        textMatchesDrawn.setText(Integer.toString(team.getDraws()));
        textMatchesLost.setText(Integer.toString(team.getLosses()));

        try {
            java.io.FileInputStream imageLoader = new FileInputStream("XML/Savegames/" + Main.getCompetition().getSaveGameId() + "/images/" + Main.getChosenTeamName() + ".png");
            Image image = new Image(imageLoader, 200, 200, true, false);
            teamLogo.setImage(image);
            imageLoader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Image could not be found");
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(GameScreenHOMEController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
