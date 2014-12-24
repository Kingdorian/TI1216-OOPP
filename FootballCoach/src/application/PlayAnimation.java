package application;

import application.animation.CalculateMatch.MainAIController;
import application.animation.ContainerPackage.AnimatedMatch;
import application.animation.Playmatch.AnimateFootballMatch;
import application.model.Goalkeeper;
import application.model.Match;
import application.model.Player;
import application.model.Team;
import java.util.ArrayList;

public class PlayAnimation{

    //****************************************************************************************************
    // End of coppied code.
    

    public static Match playAnimation(Team team1, Goalkeeper keeperTeam1, ArrayList<Player> playersTeam1, Team team2, Goalkeeper keeperTeam2, ArrayList<Player> playersTeam2){
        
        AnimatedMatch testMatch = MainAIController.createMatch();

        AnimateFootballMatch.playMatch(testMatch);
        
        // TODO: write methods to get the score more easilly
        int scoreLeft = testMatch.getPositions(testMatch.amoutOfSlices()-1).getScoreLeft();
        int scoreRight = testMatch.getPositions(testMatch.amoutOfSlices()-1).getScoreRight();
        
        return new Match(team1, team2, scoreLeft, scoreRight);
    }
    
}
