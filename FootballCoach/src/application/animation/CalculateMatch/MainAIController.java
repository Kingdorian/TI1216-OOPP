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

/**
 *
 * @author faris
 */
public class MainAIController {
    private static AnimatedMatch footballMatch = new AnimatedMatch();
    private static CurrentPositions currentPositions = new CurrentPositions();
    public static final int AMOUNT_OF_SLICES = 21600; // should be 21600
    
    
    public static AnimatedMatch createMatch(){
        
        // set player abilities and favorite positions
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95, new ExactPosition(60 , 381)), 0);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(330, 160)), 1);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(288, 290)), 2);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(288, 467)), 3);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(330, 605)), 4);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(550, 250)), 5);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(450, 385)), 6);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(550, 513)), 7);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(713, 259)), 8);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(785, 385)), 9);
        CurrentPositions.setAllyInfo(new PlayerInfo(95,95,95, new ExactPosition(719, 494)), 10);

        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95, new ExactPosition(963, 381)), 0);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(700, 160)), 1);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(730, 290)), 2);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(730, 467)), 3);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(700, 605)), 4);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(461, 250)), 5);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(562, 385)), 6);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(461, 513)), 7);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(306, 259)), 8);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(233, 385)), 9);
        CurrentPositions.setEnemyInfo(new PlayerInfo(95,95,95, new ExactPosition(306, 494)), 10);
        
        
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
            
            
            // get defenders next actions
            for(int j=1; j<5; j++){
                ppA = (new DefenderAI(currentPositions.getAllyTeam().get(j), currentPositions, true, j)).getNextPosition();
                ppE = (new DefenderAI(currentPositions.getEnemyTeam().get(j), currentPositions, false, j)).getNextPosition();

                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
                
                nextPositions.getEnemyTeam().get(j).setxPos(ppE.getxPos());
                nextPositions.getEnemyTeam().get(j).setyPos(ppE.getyPos());
            }
            
            
            // get midfielders next actions
            for(int j=5; j<8; j++){
                ppA = (new MidfieldAI(currentPositions.getAllyTeam().get(j), currentPositions, true, j)).getNextPosition();
                ppE = (new MidfieldAI(currentPositions.getEnemyTeam().get(j), currentPositions, false, j)).getNextPosition();

                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
                
                nextPositions.getEnemyTeam().get(j).setxPos(ppE.getxPos());
                nextPositions.getEnemyTeam().get(j).setyPos(ppE.getyPos());
            }
            
            
            // get attackers next actions
            for(int j=8; j<11; j++){
                ppA = (new AttackerAI(currentPositions.getAllyTeam().get(j), currentPositions, true, j)).getNextPosition();
                ppE = (new AttackerAI(currentPositions.getEnemyTeam().get(j), currentPositions, false, j)).getNextPosition();

                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
                
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
            nextPositions.convertToTimeSlice();
//            if(i % ((AMOUNT_OF_SLICES-1)/10) == 0)
//                System.out.println(CurrentPositions.getScoreLeft() + " - " + CurrentPositions.getScoreRight());
            //*************************************
            
            currentPositions = nextPositions;
        }
        
       // getEnemiesInFrontOf(currentSlice.getPlayersAdversary(index));
        return footballMatch;
    }
}
