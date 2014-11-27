/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculateMatch;

import ContainerPackage.ExactPosition;
import ContainerPackage.Match;
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
        
        // set start of match
        currentPositions.setStartOfMatchPositions();
        PositionsTimeSlice currentSlice = currentPositions.convertToTimeSlice();
        footballMatch.addPositionSlice(currentSlice);
        BallAI.setCurrentBallPosition(currentPositions.getBallPosition());
        
        // set the rest of the match
        for(int i=0; i<180; i++){
            ExactPosition pp = (new AttackerAI(currentPositions.getEnemyTeam().get(9), currentPositions, false)).getNextPosition();
            CurrentPositions nextPositions = (new CurrentPositions());
            nextPositions.setStartOfMatchPositions();
            
            nextPositions.getEnemyTeam().get(9).setxPos(pp.getxPos());
            nextPositions.getEnemyTeam().get(9).setyPos(pp.getyPos());
            nextPositions.setBallPosition(BallAI.getNextBallPosition());
            
            footballMatch.addPositionSlice(nextPositions.convertToTimeSlice());
            currentPositions = nextPositions;
        }
        
       // getEnemiesInFrontOf(currentSlice.getPlayersAdversary(index));
        
        return footballMatch;
    }
}
