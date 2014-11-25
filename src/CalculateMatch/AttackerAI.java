package CalculateMatch;

import ContainerPackage.ExactPosition;
import java.util.ArrayList;

/**
 * This class can be used to calculate the next position an attacker should move to.
 * @author faris
 */
public class AttackerAI extends PlayerAI {
    private final ExactPosition thisAttacker;
    private final CurrentPositions positions;
    private final boolean isOnAllyTeam;
    
    
    /**
     * constructor: needs the position of the attacker and the positions of other players
     * @param thisAttacker  the position of this attacker
     * @param positions     the previous positions of all other players
     */
    public AttackerAI(ExactPosition thisAttacker, CurrentPositions positions){
        this.thisAttacker = thisAttacker;
        this.positions = positions;
        isOnAllyTeam = positions.isAlly(thisAttacker);
    }
    
    
    /**
     * gives the position the attacker want to move to
     * @return  the ExactPosition the attacker want to move to
     */
    @Override
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

                // colse enough to ball: decide direction to kick ball
                ArrayList<ExactPosition> opponents = positions.getEnemiesInFrontOf(thisAttacker);
                   
                if(opponents.size() > 1){
                    
                    // get the closest opponent to the player
                    ExactPosition closest = opponents.get(0);
                    for(int i=1; i < opponents.size(); i++)
                        if(thisAttacker.distanceTo(opponents.get(i)) < thisAttacker.distanceTo(closest))
                            closest = opponents.get(i);

                    // get the second closest opponent to the player
                    ExactPosition secondClosest = null;
                    for(ExactPosition p : opponents)
                        if(thisAttacker.distanceTo(p) > thisAttacker.distanceTo(closest) && thisAttacker.distanceTo(p) < thisAttacker.distanceTo(secondClosest) && Math.abs(p.getyPos() - closest.getyPos()) > 60)
                            secondClosest = p;
                    if(secondClosest == null)
                        secondClosest = new ExactPosition(113, 276);

                    // get direction to shoot ball to
                    double xDirection = closest.getxPos() - thisAttacker.getxPos() + secondClosest.getxPos();
                    double yDirection = closest.getyPos() - thisAttacker.getyPos() + secondClosest.getyPos();
                    ExactPosition direction = new ExactPosition(xDirection, yDirection);
                    
                    // shoot ball
                    BallAI.shootBallTo(direction);
                    
                    //move toward where you shot the ball to
                    return getPosBySpeed(WITHBALLSPEED, thisAttacker, direction);
                } else if(thisAttacker.distanceTo(new ExactPosition(60, 381)) < 50){
                    System.out.println("shoot at goal: not implemented yet");
                } else{
                    ExactPosition goalPosition = new ExactPosition(60,381);
                    BallAI.shootBallTo(goalPosition);
                    
                    return getPosBySpeed(WITHBALLSPEED, thisAttacker, goalPosition);
                }
            }
        return getPosBySpeed(RUNNINGSPEED, thisAttacker, BallAI.getCurrentBallPosition());
     }
}
