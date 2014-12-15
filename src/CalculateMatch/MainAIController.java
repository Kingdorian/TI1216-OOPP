/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculateMatch;

import ContainerPackage.ExactPosition;
import ContainerPackage.Match;
import ContainerPackage.PlayerInfo;
import ContainerPackage.PositionsTimeSlice;

/**
 *
 * @author faris
 */
public class MainAIController {
    private static Match footballMatch = new Match();
    private static CurrentPositions currentPositions = new CurrentPositions();
   // public static Position ballPosition = new Position();
    
    
    public static Match createMatch(){
        
        // set player abilities and favorite positions
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(60 , 381)), 0);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(330, 160)), 1);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(288, 290)), 2);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(288, 467)), 3);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(330, 605)), 4);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(550, 250)), 5);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(450, 385)), 6);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(550, 513)), 7);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(713, 259)), 8);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(785, 385)), 9);
        CurrentPositions.setAllyInfo(new PlayerInfo(70,70,70, new ExactPosition(719, 494)), 10);

        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(963, 381)), 0);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(700, 160)), 1);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(730, 290)), 2);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(730, 467)), 3);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(700, 605)), 4);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(461, 250)), 5);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(562, 385)), 6);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(461, 513)), 7);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(306, 259)), 8);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(233, 385)), 9);
        CurrentPositions.setEnemyInfo(new PlayerInfo(70,70,70, new ExactPosition(306, 494)), 10);
        
        
        // set start of match
        currentPositions.setStartOfMatchPositions();
        PositionsTimeSlice currentSlice = currentPositions.convertToTimeSlice();
        footballMatch.addPositionSlice(currentSlice);
        BallAI.setCurrentBallPosition(currentPositions.getBallPosition());
        
        // set the rest of the match
        for(int i=0; i<21600; i++){
            ExactPosition ppA;
            ExactPosition ppE;
            
            CurrentPositions nextPositions = new CurrentPositions();
            for(int j=0; j<11; j++){
                ppA = (new AttackerAI(currentPositions.getAllyTeam().get(j), currentPositions, true)).getNextPosition();
                ppE = (new AttackerAI(currentPositions.getEnemyTeam().get(j), currentPositions, false)).getNextPosition();
                
                
                //nextPositions.setStartOfMatchPositions();

                nextPositions.getAllyTeam().get(j).setxPos(ppA.getxPos());
                nextPositions.getAllyTeam().get(j).setyPos(ppA.getyPos());
                
                nextPositions.getEnemyTeam().get(j).setxPos(ppE.getxPos());
                nextPositions.getEnemyTeam().get(j).setyPos(ppE.getyPos());

            }

            
            nextPositions.setBallPosition(BallAI.getNextBallPosition());
            
            footballMatch.addPositionSlice(nextPositions.convertToTimeSlice());
            currentPositions = nextPositions;
        }
        
       // getEnemiesInFrontOf(currentSlice.getPlayersAdversary(index));
        return footballMatch;
    }
}
