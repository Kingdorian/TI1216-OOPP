/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculateMatch;

import ContainerPackage.ExactPosition;

/**
 * This class contains functions which each kind of player regularly uses
 * @author faris
 */
public abstract class PlayerAI {
    
    public static final double RUNNING_SPEED = 10;
    public static final double WITH_BALL_SPEED = 3;
    public static final double WALK_SPEED = 6;
    public static final ExactPosition LEFT_GOAL_POSITION = new ExactPosition(60,381);
    public static final ExactPosition RIGHT_GOAL_POSITION = new ExactPosition(955,381);
    public static final int MIDDLE_LINE_X = 510;

    
    public abstract ExactPosition getNextPosition();
    
    /**
     * Decide the position to move to, based on a direction, a current position and a speed
     * @param speed             double: the speed
     * @param currentLocation   ExactPosition: the current location of the player
     * @param direction         ExactPosition: the location to move toward
     * @return                  the position to move to in 1 time slice
     */
    public static ExactPosition getPosBySpeed(double speed, ExactPosition currentLocation, ExactPosition direction){
        if(currentLocation.distanceTo(direction) != 0){
            double factor =  speed/currentLocation.distanceTo(direction);

            double xPos = (direction.getxPos() - currentLocation.getxPos()) * factor + currentLocation.getxPos();
            double yPos = (direction.getyPos() - currentLocation.getyPos()) * factor + currentLocation.getyPos();
            return new ExactPosition((int) xPos,(int) yPos);
        } else
            return currentLocation;
    }
    
    public static ExactPosition moveToLeftGoal(double speed, ExactPosition currentLocation){
        return getPosBySpeed(speed, currentLocation, LEFT_GOAL_POSITION);
    }
    
    public static ExactPosition moveToRightGoal(double speed, ExactPosition currentLocation){
        return getPosBySpeed(speed, currentLocation, RIGHT_GOAL_POSITION);
    }
    
    public static ExactPosition defaultPreferredDirection(ExactPosition playerPosition, int playerID, boolean isOnAllyTeam){
        ExactPosition destination = new ExactPosition();
        destination.setxPos(BallAI.getCurrentBallPosition().getxPos());
        if(isOnAllyTeam)
            destination.setyPos(CurrentPositions.getAllyInfo(playerID).getFavoritePosition().getyPos());
        else
            destination.setyPos(CurrentPositions.getEnemyInfo(playerID).getFavoritePosition().getyPos());
        return getPosBySpeed(WALK_SPEED, playerPosition, destination);
    }
    
    public static boolean enoughLuckToShootBall(ExactPosition thisAttacker, int playerID, CurrentPositions positions, boolean isOnAllyTeam){
        ExactPosition closest;
        if(isOnAllyTeam)
                        closest = positions.getClosestAllyTo(BallAI.getCurrentBallPosition());
                    else
                        closest = positions.getClosestEnemyTo(BallAI.getCurrentBallPosition());
        double opponentDistance = closest.distanceTo(BallAI.getCurrentBallPosition());
        double ownDistance = thisAttacker.distanceTo(BallAI.getCurrentBallPosition());
        int opponentID = positions.getPlayerID(closest);
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
        if(opponentDistance != 0 && ownDistance != 0)
            chance = (ownDistance / thisAttackPower)/(opponentDistance / opponentDefense);
        else if(opponentDistance == 0)
            chance = 0.001;
        else
            chance = 1000;

        // if player got enough luck
        return chance * Math.random() < Math.random()/chance;
    }
}
