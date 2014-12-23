/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.CalculateMatch;


import application.animation.ContainerPackage.ExactPosition;
import java.util.ArrayList;

/**
 * This class contains functions which each kind of player regularly uses
 * @author faris
 */
public abstract class PlayerAI {
    
    public final double RUNNING_SPEED; // ~10
    public static final double WITH_BALL_SPEED = 4;
    public static final double WALK_SPEED = 6;
    public static final ExactPosition LEFT_GOAL_POSITION = new ExactPosition(60,381);
    public static final ExactPosition RIGHT_GOAL_POSITION = new ExactPosition(955,381);
    public static final int GOAL_SIZE = 80;
    public static final int MIDDLE_LINE_X = 510;

    
    public abstract ExactPosition getNextPosition();
    
    public PlayerAI(int id, boolean isOnAllyTeam){
        if(isOnAllyTeam)
            RUNNING_SPEED = 7 + CurrentPositions.getAllyInfo(id).getStamina()/20;
        else
            RUNNING_SPEED = 7 + CurrentPositions.getEnemyInfo(id).getStamina()/20;
    }
    /**
     * Decide the position to move to, based on a direction, a current position and a speed
     * @param speed             double: the speed
     * @param currentLocation   ExactPosition: the current location of the player
     * @param direction         ExactPosition: the location to move toward
     * @return                  the position to move to in 1 time slice
     */
    public static ExactPosition getPosBySpeed(double speed, ExactPosition currentLocation, ExactPosition direction){
        if(currentLocation.distanceTo(direction) > 10){
            double factor =  speed/currentLocation.distanceTo(direction);

            double xPos = (direction.getxPos() - currentLocation.getxPos()) * factor + currentLocation.getxPos();
            double yPos = (direction.getyPos() - currentLocation.getyPos()) * factor + currentLocation.getyPos();
            return new ExactPosition((int) xPos,(int) yPos);
        } else 
            return direction;
    }

    
    public static boolean enoughLuckToDefendBall(ExactPosition thisPlayer, int playerID, CurrentPositions positions, boolean isOnAllyTeam){
        // if very close, return true, to avoid bugs
        if(thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 10)
            return true;
        ExactPosition closest;
        if(isOnAllyTeam)
                        closest = positions.getClosestAllyTo(BallAI.getCurrentBallPosition());
                    else
                        closest = positions.getClosestEnemyTo(BallAI.getCurrentBallPosition());
        double opponentDistance = closest.distanceTo(BallAI.getCurrentBallPosition());
        double ownDistance = thisPlayer.distanceTo(BallAI.getCurrentBallPosition());
        int opponentID = positions.getIDByPosition(closest);
        double opponentAttack;
        double thisDefensePower;
        if(isOnAllyTeam){
            opponentAttack = CurrentPositions.getEnemyInfo(opponentID).getAttackPower();
            thisDefensePower =  CurrentPositions.getAllyInfo(playerID).getDefensePower();
        } else{
            opponentAttack = CurrentPositions.getAllyInfo(opponentID).getAttackPower();
            thisDefensePower =  CurrentPositions.getEnemyInfo(playerID).getDefensePower();
        }
        double chance;
        //if(opponentDistance != 0 && ownDistance != 0)
            chance = ((ownDistance+10) / thisDefensePower)/((opponentDistance+10) / opponentAttack);
//        else if(opponentDistance == 0)
//            chance = 0.001;
//        else
//            chance = 1000;

        // if player got enough luck
        return chance * Math.random() < Math.random()/chance;
        
    }
    
    public static boolean enoughLuckToShootBall(ExactPosition thisPlayer, int playerID, CurrentPositions positions, boolean isOnAllyTeam){
        
        // if very close, return true, to avoid bugs
        if(thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 10)
            return true;
        
        ExactPosition closest;
        if(isOnAllyTeam)
                        closest = positions.getClosestAllyTo(BallAI.getCurrentBallPosition());
                    else
                        closest = positions.getClosestEnemyTo(BallAI.getCurrentBallPosition());
        double opponentDistance = closest.distanceTo(BallAI.getCurrentBallPosition());
        double ownDistance = thisPlayer.distanceTo(BallAI.getCurrentBallPosition());
        int opponentID = positions.getIDByPosition(closest);
        double opponentDefense;
        double thisAttackPower;
        if(isOnAllyTeam){
            opponentDefense = CurrentPositions.getEnemyInfo(opponentID).getDefensePower();
            thisAttackPower =  CurrentPositions.getAllyInfo(playerID).getAttackPower();
        } else{
            opponentDefense = CurrentPositions.getAllyInfo(opponentID).getDefensePower();
            thisAttackPower =  CurrentPositions.getEnemyInfo(playerID).getAttackPower();
        }
        double chance;
//        if(opponentDistance != 0 && ownDistance != 0)
            chance = ((ownDistance+10) / thisAttackPower)/((opponentDistance+10) / opponentDefense);
//        else if(opponentDistance == 0)
//            chance = 0.001;
//        else
//            chance = 1000;

        // if player got enough luck
        return chance * Math.random() < Math.random()/chance;
    }
    
    
    public static ExactPosition[] get2ClosestPlayers(ExactPosition thisPlayer, CurrentPositions positions){
        
        ArrayList<ExactPosition> opponents = positions.getOpponentsInFrontOf(thisPlayer);
        if(opponents.size() > 0){
            // get the closest opponent to the player
            ExactPosition closest = opponents.get(0);
            for (int i = 1; i < opponents.size(); i++) {
                if (thisPlayer.distanceTo(opponents.get(i)) < thisPlayer.distanceTo(closest)) {
                    closest = opponents.get(i);
                }
            }

            // get the second closest opponent to the player
            ExactPosition secondClosest = null;
            for (ExactPosition p : opponents) {
                if (thisPlayer.distanceTo(p) > thisPlayer.distanceTo(closest) && thisPlayer.distanceTo(p) < thisPlayer.distanceTo(secondClosest) && Math.abs(p.getyPos() - closest.getyPos()) > 60) {
                    secondClosest = p;
                }
            }
            ExactPosition result[] = {closest, secondClosest};
            return result;
        } else{
            return null;
        }
    }
    
    
    public static ExactPosition getDirectionInBetween(ExactPosition thisPlayer, ExactPosition opponent1, ExactPosition opponent2){
        
        double xDirection = opponent1.getxPos() - thisPlayer.getxPos() + opponent2.getxPos();
        double yDirection = opponent1.getyPos() - thisPlayer.getyPos() + opponent2.getyPos();
        ExactPosition direction = new ExactPosition(xDirection, yDirection);
        
        return direction;
    }
}
