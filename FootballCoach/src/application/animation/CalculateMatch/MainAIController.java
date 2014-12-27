/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.CalculateMatch;

import application.animation.ContainerPackage.CurrentPositions;
import application.animation.ContainerPackage.ExactPosition;
import application.animation.ContainerPackage.AnimatedMatch;
import application.animation.ContainerPackage.PlayerInfo;
import application.animation.ContainerPackage.PositionsTimeSlice;
import java.util.ArrayList;

/**
 *
 * @author faris
 */
public class MainAIController {
    private final AnimatedMatch footballMatch = new AnimatedMatch();
    private CurrentPositions currentPositions = new CurrentPositions();
    public static final int AMOUNT_OF_SLICES = 21600; // should be 21600
    
    
    public AnimatedMatch createMatch(PlayerInfo keeper1, ArrayList<PlayerInfo> defense1,
            ArrayList<PlayerInfo> midfield1, ArrayList<PlayerInfo> attack1,
            PlayerInfo keeper2, ArrayList<PlayerInfo> defense2,
            ArrayList<PlayerInfo> midfield2, ArrayList<PlayerInfo> attack2){
        
        // count amout of defenders, midfielders and attackers of both teams 
        // (and make it easier to apply in a loop)
        final ArrayList<Integer> formationAlly = new ArrayList<>();
        formationAlly.add(defense1.size());
        formationAlly.add(defense1.size()+midfield1.size());
        
        final ArrayList<Integer> formationEnemy = new ArrayList<>();
        formationEnemy.add(defense2.size());
        formationEnemy.add(defense2.size()+midfield2.size());
        
        // set player abilities and favorite positions of ally team
        currentPositions.setAllyInfo(keeper1, 0);
        int counter = 1;
        for(PlayerInfo pi : defense1)
            currentPositions.setAllyInfo(pi, counter++);
        for(PlayerInfo pi : midfield1)
            currentPositions.setAllyInfo(pi, counter++);
        for(PlayerInfo pi : attack1)
            currentPositions.setAllyInfo(pi, counter++);
        
        // set player abilities and favorite positions of enemy team
        currentPositions.setEnemyInfo(keeper2, 0);
        counter = 1;
        for(PlayerInfo pi : defense2)
            currentPositions.setEnemyInfo(pi, counter++);
        for(PlayerInfo pi : midfield2)
            currentPositions.setEnemyInfo(pi, counter++);
        for(PlayerInfo pi : attack2)
            currentPositions.setEnemyInfo(pi, counter++);
        
        
        // set start of match
        currentPositions.setStartOfMatchPositions();
        PositionsTimeSlice currentSlice = currentPositions.convertToTimeSlice();
        footballMatch.addPositionSlice(currentSlice);
        
        // set the rest of the match
        for(int i=0; i<AMOUNT_OF_SLICES; i++){
            ExactPosition ppA;
            ExactPosition ppE;
            
            CurrentPositions nextPositions = new CurrentPositions();
            
            
            // get keepers next actions
            ppA = (new KeeperAI(currentPositions.getAllyTeam().get(0), currentPositions, true, 0)).getNextPosition();
            ppE = (new KeeperAI(currentPositions.getEnemyTeam().get(0), currentPositions, false, 0)).getNextPosition();

            nextPositions.getAllyTeam().get(0).setxPos(ppA.getxPos());
            nextPositions.getAllyTeam().get(0).setyPos(ppA.getyPos());

            nextPositions.getEnemyTeam().get(0).setxPos(ppE.getxPos());
            nextPositions.getEnemyTeam().get(0).setyPos(ppE.getyPos());
            
            
            // get defenders of ally team next actions
            for(int j=1; j<formationAlly.get(0); j++){
                ppA = (new DefenderAI(currentPositions.getAllyTeam().get(j), currentPositions, true, j)).getNextPosition();
                
                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
            }
            
            // get defenders of enemy team next actions
            for(int j=1; j<formationEnemy.get(0); j++){
                ppE = (new DefenderAI(currentPositions.getEnemyTeam().get(j), currentPositions, false, j)).getNextPosition();

                nextPositions.getEnemyTeam().get(j).setxPos(ppE.getxPos());
                nextPositions.getEnemyTeam().get(j).setyPos(ppE.getyPos());
            }
            
            
            // get midfielders of ally team next actions
            for(int j=formationAlly.get(0); j<formationAlly.get(1); j++){
                ppA = (new MidfieldAI(currentPositions.getAllyTeam().get(j), currentPositions, true, j)).getNextPosition();
                
                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
            }
            
            // get midfielders of enemy team next actions
            for(int j=formationEnemy.get(0); j<formationEnemy.get(1); j++){
                ppE = (new MidfieldAI(currentPositions.getEnemyTeam().get(j), currentPositions, false, j)).getNextPosition();

                nextPositions.getEnemyTeam().get(j).setxPos(ppE.getxPos());
                nextPositions.getEnemyTeam().get(j).setyPos(ppE.getyPos());
            }
            
            
            // get attackers next actions
            for(int j=formationAlly.get(1); j<11; j++){
                ppA = (new AttackerAI(currentPositions.getAllyTeam().get(j), currentPositions, true, j)).getNextPosition();
                
                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
            }
            
            for(int j=formationEnemy.get(1); j<11; j++){
                ppE = (new AttackerAI(currentPositions.getEnemyTeam().get(j), currentPositions, false, j)).getNextPosition();

                nextPositions.getEnemyTeam().get(j).setxPos(ppE.getxPos());
                nextPositions.getEnemyTeam().get(j).setyPos(ppE.getyPos());
            }
            
            
            BallAI.setCurrentBallPosition(BallAI.getNextBallPosition());
            
            //*************************************
            //generate match and safe it
            footballMatch.addPositionSlice(nextPositions.convertToTimeSlice());
            //*************************************
            
            //*************************************
            //generating match without saving it, only to get the score
//            nextPositions.convertToTimeSlice();
//            if(i % ((AMOUNT_OF_SLICES-1)/10) == 0)
//                System.out.println(CurrentPositions.getScoreLeft() + " - " + CurrentPositions.getScoreRight());
            //*************************************
            
            currentPositions = nextPositions;
        }
        
       // getEnemiesInFrontOf(currentSlice.getPlayersAdversary(index));
        return footballMatch;
    }
}
