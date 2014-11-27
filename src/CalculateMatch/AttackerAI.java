package CalculateMatch;

import static CalculateMatch.PlayerAI.LEFT_GOAL_POSITION;
import static CalculateMatch.PlayerAI.MIDDLE_LINE_X;
import static CalculateMatch.PlayerAI.RUNNING_SPEED;
import static CalculateMatch.PlayerAI.WITH_BALL_SPEED;
import static CalculateMatch.PlayerAI.getPosBySpeed;
import static CalculateMatch.PlayerAI.moveToLeftGoal;
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
     * @param isOnAllyTeam  boolean conaining if this player is on the ally team
     */
    public AttackerAI(ExactPosition thisAttacker, CurrentPositions positions, boolean isOnAllyTeam){
        this.thisAttacker = thisAttacker;
        this.positions = positions;
        this.isOnAllyTeam = isOnAllyTeam;
    }
    
    
    /**
     * gives the position the attacker want to move to
     * @return  the ExactPosition the attacker want to move to
     */
    @Override
    public ExactPosition getNextPosition(){
        if(isOnAllyTeam){
            if(thisAttacker.equals(positions.getClosestAllyTo(positions.getBallPosition()))) // if closest player of own team to the ball
                   return moveTowardBallAlly();
            else if(BallAI.getCurrentBallPosition().getxPos() < MIDDLE_LINE_X)
                return attackingAlly();
            else
                return defendingAlly();
        } else if(thisAttacker.equals(positions.getClosestEnemyTo(positions.getBallPosition()))) // if closest player of own team to the ball
                return moveTowardBallEnemy();
            else if(BallAI.getCurrentBallPosition().getxPos() < MIDDLE_LINE_X)
                return attackingEnemy();
            else
                return defendingEnemy();
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
     * This player is on the ally team and the ball is on the other side (so his team will be attacking)
     */
    private ExactPosition attackingAlly(){
        // do stuff an attacking attacker should do
        return null;
    }
    
    
    /**
     * This player is on the ally team and the ball is on their side (so his team will be defending)
     */
    private ExactPosition defendingAlly(){
        // do stuff a defending attacker should do
        return null;
    }
    
    
    
    
    /**
     * this player is the closest player of the enemy team to the ball, so move toward it
     */
    private ExactPosition moveTowardBallEnemy(){
        if(positions.isClosestToBall(thisAttacker))
            if(thisAttacker.distanceTo(positions.getBallPosition()) < 25){

                // close enough to ball: decide direction to kick ball
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
                    if(secondClosest != null){
                        //secondClosest = new ExactPosition(113, 276);

                    // get direction to shoot ball to
                    double xDirection = closest.getxPos() - thisAttacker.getxPos() + secondClosest.getxPos();
                    double yDirection = closest.getyPos() - thisAttacker.getyPos() + secondClosest.getyPos();
                    ExactPosition direction = new ExactPosition(xDirection, yDirection);
                    
                    // shoot ball
                    BallAI.shootBallTo(direction);
                    
                    //move toward where you shot the ball to
                    return getPosBySpeed(WITH_BALL_SPEED, thisAttacker, direction);
                    }
                } else if(thisAttacker.distanceTo(LEFT_GOAL_POSITION) < 200){
                    System.out.println("shoot at goal: not implemented yet");
                    BallAI.shootLeftGoal();
                } else{                  
                    return moveToLeftGoal(WITH_BALL_SPEED, thisAttacker);
                    //return getPosBySpeed(WITH_BALL_SPEED, thisAttacker, LEFT_GOAL_POSITION);
                }
            }
        return getPosBySpeed(RUNNING_SPEED, thisAttacker, BallAI.getCurrentBallPosition());
     }
    
    
    /**
     * This player is on the enemy team and the ball is on the other side (so his team will be attacking)
     */
    private ExactPosition attackingEnemy(){
        
        return null;
    }
    
    
    /**
     * This player is on the enemy team and the ball is on their side (so his team will be defending)
     */
    private ExactPosition defendingEnemy(){
        // do stuff a defending attacker should do
        return null;
    }
}
