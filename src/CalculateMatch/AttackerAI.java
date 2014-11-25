/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculateMatch;

import ContainerPackage.ExactPosition;
import java.util.ArrayList;

/**
 *
 * @author faris
 */
public class AttackerAI {
    private final ExactPosition thisAttacker;
    private final CurrentPositions positions;
    private final boolean isOnAllyTeam;
    
    
    public AttackerAI(ExactPosition thisAttacker, CurrentPositions positions){
        this.thisAttacker = thisAttacker;
        this.positions = positions;
        isOnAllyTeam = positions.isAlly(thisAttacker);
    }
    
    
    public ExactPosition getNextPosition(){
        if(isOnAllyTeam){
            if(thisAttacker.equals(positions.getClosestAllyTo(positions.getBallPosition()))){ // if closest player of own team to the ball
                   return moveTowardBallAlly();
            }
        } else
            if(thisAttacker.equals(positions.getClosestEnemyTo(positions.getBallPosition()))) // if closest player of own team to the ball
                   return moveTowardBallEnemy();
            else{
                ExactPosition p = positions.getClosestEnemyTo(positions.getBallPosition());
                System.out.println("Closest (x,y):" + p.getxPos() + p.getyPos());
                System.out.println("But was (x,y):" + thisAttacker.getxPos() + thisAttacker.getyPos());
            }
            
        return thisAttacker;
    }
    
    
    /**
     * this player is the closest player of the ally team to the ball, so move toward it
     */
    private ExactPosition moveTowardBallAlly(){
//        if(positions.isClosestToBall(thisAttacker))
//            if(thisAttacker.distanceTo(positions.getBallPosition()) < 25)
//                MainAIController.ballPosition = new Position(positions.getBallPosition().getxPos() + 40 , thisAttacker.getyPos());
//        return new Position(thisAttacker.getxPos() + 10, thisAttacker.getyPos());
        return new ExactPosition(thisAttacker.getxPos(),thisAttacker.getyPos());
    }
    
        /**
     * this player is the closest player of the enemy team to the ball, so move toward it
     */
    private ExactPosition moveTowardBallEnemy(){
        if(positions.isClosestToBall(thisAttacker))
            if(thisAttacker.distanceTo(positions.getBallPosition()) < 25){

                // decide direction to kick ball
                ArrayList<ExactPosition> opponents = positions.getEnemiesInFrontOf(thisAttacker);
                   
                if(opponents.size() > 1){
                    
                    ExactPosition closest = opponents.get(0);
                    for(int i=1; i < opponents.size(); i++)
                        if(thisAttacker.distanceTo(opponents.get(i)) < thisAttacker.distanceTo(closest))
                            closest = opponents.get(i);

                    ExactPosition secondClosest = null;
                    for(ExactPosition p : opponents)
                        if(thisAttacker.distanceTo(p) > thisAttacker.distanceTo(closest) && thisAttacker.distanceTo(p) < thisAttacker.distanceTo(secondClosest) && Math.abs(p.getyPos() - closest.getyPos()) > 60)
                            secondClosest = p;
                    if(secondClosest == null)
                        secondClosest = new ExactPosition(113, 276);

                    double xDirection = closest.getxPos() - thisAttacker.getxPos() + secondClosest.getxPos();
                    double yDirection = closest.getyPos() - thisAttacker.getyPos() + secondClosest.getyPos();
                    ExactPosition direction = new ExactPosition(xDirection, yDirection);

                    BallAI.shootBallTo(direction);
//                    double factor = 60.0/thisAttacker.distanceTo(direction);
//                    double xBall = (direction.getxPos() - thisAttacker.getxPos()) * factor + thisAttacker.getxPos();
//                    double yBall = (direction.getyPos() - thisAttacker.getyPos()) * factor + thisAttacker.getyPos();

//                    MainAIController.ballPosition = new Position((int) xBall, (int) yBall); // kick ball

                    double factor = 7.5/thisAttacker.distanceTo(direction);
                    double xAttacker = (direction.getxPos() - thisAttacker.getxPos()) * factor + thisAttacker.getxPos();
                    double yAttacker = (direction.getyPos() - thisAttacker.getyPos()) * factor + thisAttacker.getyPos();
                    return new ExactPosition((int) xAttacker,(int) yAttacker);
                } else if(thisAttacker.distanceTo(new ExactPosition(60, 381)) < 50){
                    System.out.println("shoot at goal: not implemented yet");
                } else{
                    ExactPosition goalPosition = new ExactPosition(60,381);
                    BallAI.shootBallTo(goalPosition);
//                    double factor = 35.0/thisAttacker.distanceTo(goalPosition);
//                    double xBall = (goalPosition.getxPos() - thisAttacker.getxPos()) * factor + thisAttacker.getxPos();
//                    double yBall = (goalPosition.getyPos() - thisAttacker.getyPos()) * factor + thisAttacker.getyPos();
//                    MainAIController.ballPosition = new Position((int) xBall,(int) yBall); // kick ball
                    double factor =  7.5/thisAttacker.distanceTo(goalPosition);
                    double xAttacker = (goalPosition.getxPos() - thisAttacker.getxPos()) * factor + thisAttacker.getxPos();
                    double yAttacker = (goalPosition.getyPos() - thisAttacker.getyPos()) * factor + thisAttacker.getyPos();
                    return new ExactPosition((int) xAttacker,(int) yAttacker);
                }
                    
            }
        ExactPosition ballPosition = BallAI.getCurrentBallPosition();
        double factor = 10/thisAttacker.distanceTo(ballPosition);
        double xAttacker = (ballPosition.getxPos() - thisAttacker.getxPos()) * factor + thisAttacker.getxPos();
        double yAttacker = (ballPosition.getyPos() - thisAttacker.getyPos()) * factor + thisAttacker.getyPos();
        if(yAttacker == thisAttacker.getyPos())
            yAttacker = ballPosition.getyPos();
        if(xAttacker == thisAttacker.getyPos())
            xAttacker = ballPosition.getxPos();
        return new ExactPosition((int) xAttacker,(int) yAttacker);
        
        //return new Position(thisAttacker.getxPos() - 10, thisAttacker.getyPos()); // move toward ball
    }
}
