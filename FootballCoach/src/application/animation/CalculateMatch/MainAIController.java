/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.CalculateMatch;

import application.animation.Container.CurrentPositions;
import application.animation.Container.Position;
import application.animation.Container.CalculatedMatch;
import application.animation.Container.PlayerInfo;
import application.animation.Container.PositionFrame;
import application.animation.Container.TeamPositions;
import java.util.ArrayList;

/**
 * The method createMatch in this class can be used to calculate a match, based
 * on the players abilities in the parameters
 *
 * @author Faris
 */
public class MainAIController {

    public static final int AMOUNT_OF_FRAMES = 21600; // should be 21600

    /**
     * Calculate a match based on the players abilities in the parameters
     *
     * @param keeper1 the keeper of the ally (home) team
     * @param defense1 the defenders of the ally (home) team
     * @param midfield1 the midfielders of the ally (home) team
     * @param attack1 the attackers of the ally (home) team
     * @param keeper2 the keeper of the enemy (visitor) team
     * @param defense2 the defenders of the enemy (visitor) team
     * @param midfield2 the midfielders of the enemy (visitor) team
     * @param attack2 the attackers of the enemy (visitor) team
     * @param shouldAnimate if this match should be stored for animation or if only
     * the last frame for the results is important
     * @return the calculated match
     */
    public CalculatedMatch createMatch(TeamPositions homeTeam, TeamPositions visitorTeam, boolean shouldAnimate) {

        PlayerInfo keeper1 = homeTeam.getKeeper();
        ArrayList<PlayerInfo> defense1 = homeTeam.getDefenders();
        ArrayList<PlayerInfo> midfield1 = homeTeam.getMidfielders();
        ArrayList<PlayerInfo> attack1 = homeTeam.getAttackers();
        System.out.println("keeper1.toString() = " + keeper1.getFavoritePosition().getxPos() + " " +  keeper1.getFavoritePosition().getyPos());
        
        PlayerInfo keeper2 = visitorTeam.getKeeper();
        ArrayList<PlayerInfo> defense2 = visitorTeam.getDefenders();
        ArrayList<PlayerInfo> midfield2 = visitorTeam.getMidfielders();
        ArrayList<PlayerInfo> attack2 = visitorTeam.getAttackers();
        System.out.println("keeper2.toString() = " + keeper2.getFavoritePosition().getxPos() + " " +  keeper2.getFavoritePosition().getyPos());
        final CalculatedMatch footballMatch = new CalculatedMatch();
        CurrentPositions currentPositions = new CurrentPositions();

        // count amout of defenders, midfielders and attackers of both teams 
        // (and make it easier to apply in a loop)
        final ArrayList<Integer> formationAlly = new ArrayList<>();
        formationAlly.add(defense1.size());
        formationAlly.add(defense1.size() + midfield1.size());

        final ArrayList<Integer> formationEnemy = new ArrayList<>();
        formationEnemy.add(defense2.size());
        formationEnemy.add(defense2.size() + midfield2.size());

        // set player abilities and favorite positions of ally team
        currentPositions.setAllyInfo(keeper1, 0);
        int counter = 1;
        for (PlayerInfo pi : defense1) {
            currentPositions.setAllyInfo(pi, counter++);
        }
        for (PlayerInfo pi : midfield1) {
            currentPositions.setAllyInfo(pi, counter++);
        }
        for (PlayerInfo pi : attack1) {
            currentPositions.setAllyInfo(pi, counter++);
        }

        // set player abilities and favorite positions of enemy team
        currentPositions.setEnemyInfo(keeper2, 0);
        counter = 1;
        for (PlayerInfo pi : defense2) {
            currentPositions.setEnemyInfo(pi, counter++);
        }
        for (PlayerInfo pi : midfield2) {
            currentPositions.setEnemyInfo(pi, counter++);
        }
        for (PlayerInfo pi : attack2) {
            currentPositions.setEnemyInfo(pi, counter++);
        }

        // set start of match
        currentPositions.setStartOfMatchPositions();
        PositionFrame currentSlice = currentPositions.convertToFrame();
        footballMatch.addPositionFrame(currentSlice);

        // set the rest of the match
        for (int i = 0; i < AMOUNT_OF_FRAMES; i++) {
            Position ppA;
            Position ppE;

            CurrentPositions nextPositions = new CurrentPositions();

            // get keepers next actions
            ppA = (new KeeperAI(currentPositions.getAllyTeam().get(0), currentPositions, true, 0)).getNextPosition();
            ppE = (new KeeperAI(currentPositions.getEnemyTeam().get(0), currentPositions, false, 0)).getNextPosition();

            nextPositions.getAllyTeam().get(0).setxPos(ppA.getxPos());
            nextPositions.getAllyTeam().get(0).setyPos(ppA.getyPos());

            nextPositions.getEnemyTeam().get(0).setxPos(ppE.getxPos());
            nextPositions.getEnemyTeam().get(0).setyPos(ppE.getyPos());

            // get defenders of ally team next actions
            for (int j = 1; j < formationAlly.get(0); j++) {
                ppA = (new DefenderAI(currentPositions.getAllyTeam().get(j), currentPositions, true, j)).getNextPosition();

                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
            }

            // get defenders of enemy team next actions
            for (int j = 1; j < formationEnemy.get(0); j++) {
                ppE = (new DefenderAI(currentPositions.getEnemyTeam().get(j), currentPositions, false, j)).getNextPosition();

                nextPositions.getEnemyTeam().get(j).setxPos(ppE.getxPos());
                nextPositions.getEnemyTeam().get(j).setyPos(ppE.getyPos());
            }

            // get midfielders of ally team next actions
            for (int j = formationAlly.get(0); j < formationAlly.get(1); j++) {
                ppA = (new MidfieldAI(currentPositions.getAllyTeam().get(j), currentPositions, true, j)).getNextPosition();

                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
            }

            // get midfielders of enemy team next actions
            for (int j = formationEnemy.get(0); j < formationEnemy.get(1); j++) {
                ppE = (new MidfieldAI(currentPositions.getEnemyTeam().get(j), currentPositions, false, j)).getNextPosition();

                nextPositions.getEnemyTeam().get(j).setxPos(ppE.getxPos());
                nextPositions.getEnemyTeam().get(j).setyPos(ppE.getyPos());
            }

            // get attackers next actions
            for (int j = formationAlly.get(1); j < 11; j++) {
                ppA = (new AttackerAI(currentPositions.getAllyTeam().get(j), currentPositions, true, j)).getNextPosition();

                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
            }

            for (int j = formationEnemy.get(1); j < 11; j++) {
                ppE = (new AttackerAI(currentPositions.getEnemyTeam().get(j), currentPositions, false, j)).getNextPosition();

                nextPositions.getEnemyTeam().get(j).setxPos(ppE.getxPos());
                nextPositions.getEnemyTeam().get(j).setyPos(ppE.getyPos());
            }

            BallAI.setCurrentBallPosition(BallAI.getNextBallPosition());

            
            //generate match and save it
            if(shouldAnimate || i == AMOUNT_OF_FRAMES - 1)
                footballMatch.addPositionFrame(nextPositions.convertToFrame());
            else
                nextPositions.convertToFrame(); // do not save the frame (so only the last frame will be saved

            currentPositions = nextPositions;
        }
        
        return footballMatch;
    }
}
