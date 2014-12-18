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
    private final int playerID;
    
    
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
        playerID = positions.getPlayerID(thisAttacker);
    }
    
    
    /**
     * gives the position the attacker want to move to
     * @return  the ExactPosition the attacker want to move to
     */
    @Override
    public ExactPosition getNextPosition(){
        
        // <REMOVE AFTER TESTING>
//        if(playerID < 8)
//            return thisAttacker;
        // </REMOVE AFTER TESTING>
        
        if(isOnAllyTeam){
            if(thisAttacker.equals(positions.getClosestAllyTo(positions.getBallPosition()))) // if closest player of own team to the ball
                return moveTowardBall();
            else if(BallAI.getCurrentBallPosition().getxPos() > MIDDLE_LINE_X)
                return attackingPlayer();
            else
                return defendingPlayer();
        } else if(thisAttacker.equals(positions.getClosestEnemyTo(positions.getBallPosition()))) // if closest player of own team to the ball
                return moveTowardBall();
            else if(BallAI.getCurrentBallPosition().getxPos() < MIDDLE_LINE_X)
                return attackingPlayer();
            else
                return defendingPlayer();
    }

    
    
    
    
    /**
     * this player is the closest player of the enemy team to the ball, so move toward it
     */
    private ExactPosition moveTowardBall(){
        // if closest to the ball and distance to ball is smaller than 25, close enough to shoot
        if(positions.isClosestToBall(thisAttacker) && thisAttacker.distanceTo(positions.getBallPosition()) < 25)
            if((!isOnAllyTeam) && thisAttacker.distanceTo(LEFT_GOAL_POSITION) < 200){
                    BallAI.shootLeftGoal();
                } else if(isOnAllyTeam && thisAttacker.distanceTo(RIGHT_GOAL_POSITION) < 200){
                    BallAI.shootRightGoal();
                } else{
                // close enough to ball: decide direction to kick ball
                ArrayList<ExactPosition> opponents = positions.getOpponentsInFrontOf(thisAttacker);
                   
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
                        // get direction to shoot ball to
                        double xDirection = closest.getxPos() - thisAttacker.getxPos() + secondClosest.getxPos();
                        double yDirection = closest.getyPos() - thisAttacker.getyPos() + secondClosest.getyPos();
                        ExactPosition direction = new ExactPosition(xDirection, yDirection);

                        // if player got enough luck
                        if(enoughLuckToShootBall(thisAttacker, playerID, positions, isOnAllyTeam)){
                            
                            // if there is an opponent very close, decide to shoot the ball to an ally, 
                            // based on how close the opponent is. (closer = higher chance to shoot to an ally)
                            double distanceToClosest;
                            if(isOnAllyTeam)
                                distanceToClosest = positions.getClosestEnemyTo(thisAttacker).distanceTo(thisAttacker);
                            else
                                distanceToClosest = positions.getClosestAllyTo(thisAttacker).distanceTo(thisAttacker);
                            //double distanceToClosest = thisAttacker.distanceTo(closest);
                            
                            if(distanceToClosest < 100 && distanceToClosest * Math.random() < 50 * Math.random()){
                                ExactPosition closestOfOwnTeam = null;
                                if(isOnAllyTeam){
                                    for(ExactPosition pp : positions.getAllyTeam())
                                        if(pp != thisAttacker && thisAttacker.distanceTo(pp) < thisAttacker.distanceTo(closestOfOwnTeam) && positions.getClosestEnemyTo(pp).distanceTo(pp) > 50 && thisAttacker.distanceTo(pp) < 150)
                                            closestOfOwnTeam = pp;
                                } else
                                    for(ExactPosition pp : positions.getEnemyTeam())
                                        if(pp != thisAttacker && thisAttacker.distanceTo(pp) < thisAttacker.distanceTo(closestOfOwnTeam) && positions.getClosestAllyTo(pp).distanceTo(pp) > 50 && thisAttacker.distanceTo(pp) < 150)
                                            closestOfOwnTeam = pp;
                                if(closestOfOwnTeam != null){
                                    BallAI.shootToTeammate(closestOfOwnTeam, isOnAllyTeam);
                                    return getPosBySpeed(WITH_BALL_SPEED, thisAttacker, direction);
                                }
                            }
                            
                            // shoot ball
                            BallAI.shootBallTo(direction, isOnAllyTeam);

                            // move toward where you shot the ball to
                            return getPosBySpeed(WITH_BALL_SPEED, thisAttacker, direction);
                        }
                    }
                } else {
                    // if player got enough luck
                    if(enoughLuckToShootBall(thisAttacker, playerID, positions, isOnAllyTeam)){
                        if(isOnAllyTeam){
                            // shoot ball and walk to right goal
                            BallAI.shootBallTo(RIGHT_GOAL_POSITION, isOnAllyTeam);
                            return moveToRightGoal(WITH_BALL_SPEED, thisAttacker);
                        } else{
                            // shoot ball and walk to left goal
                            BallAI.shootBallTo(LEFT_GOAL_POSITION, isOnAllyTeam);
                            return moveToLeftGoal(WITH_BALL_SPEED, thisAttacker);
                        }
                    }
                }
            }
        return getPosBySpeed(RUNNING_SPEED, thisAttacker, BallAI.getCurrentBallPosition());
     }
    
    

    private ExactPosition attackingPlayer(){
        
        if((BallAI.islastShotByAllyTeam() && isOnAllyTeam) || (!BallAI.islastShotByAllyTeam() && !isOnAllyTeam)){
            ArrayList<ExactPosition> opponents = positions.getOpponentsInFrontOf(thisAttacker);
            if(opponents.size() > 2){
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
                        // set direction to x-pos of the ball and y-pos in the middle of the closest 2 opponents
                        double xDirection = BallAI.getCurrentBallPosition().getxPos();
                        double yDirection = closest.getyPos() - thisAttacker.getyPos() + secondClosest.getyPos();
                        ExactPosition direction = new ExactPosition(xDirection, yDirection);
                        return getPosBySpeed(RUNNING_SPEED, thisAttacker, direction);
                    }
            } else{
                ExactPosition destination = new ExactPosition();
                destination.setxPos(BallAI.getCurrentBallPosition().getxPos());
                destination.setyPos((CurrentPositions.getAllyInfo(playerID).getFavoritePosition().getyPos() * 2 + BallAI.getCurrentBallPosition().getyPos())/3);
                return getPosBySpeed(RUNNING_SPEED, thisAttacker, destination);
            }
        }
        
        // TODO: Solve bugs when at same x as ball <><><><><><><><><><><><><><><><><><><><><><><><>
        
        return defaultPreferredDirection(thisAttacker, playerID, isOnAllyTeam);
    }
    
    

    private ExactPosition defendingPlayer(){
        if(BallAI.islastShotByAllyTeam() && isOnAllyTeam)
            return getPosBySpeed(WALK_SPEED, thisAttacker, CurrentPositions.getAllyInfo(playerID).getFavoritePosition());
        else if(!BallAI.islastShotByAllyTeam() && !isOnAllyTeam)
             return getPosBySpeed(WALK_SPEED, thisAttacker, CurrentPositions.getEnemyInfo(playerID).getFavoritePosition());
        
        // TODO: Move toward middle line with y pos as favorite position <><><><><><><><><><><><><><><><><><><><><><><><>

        return defaultPreferredDirection(thisAttacker, playerID, isOnAllyTeam);
    }
}
