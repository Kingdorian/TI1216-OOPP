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
    public static final double WITH_BALL_SPEED = 7.5;
    public static final double WALK_SPEED = 2.5;
    public static final ExactPosition LEFT_GOAL_POSITION = new ExactPosition(60,381);
    public static final ExactPosition RIGHT_GOAL_POSITION = new ExactPosition(60,955);
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
        double factor =  speed/currentLocation.distanceTo(direction);
        double xPos = (direction.getxPos() - currentLocation.getxPos()) * factor + currentLocation.getxPos();
        double yPos = (direction.getyPos() - currentLocation.getyPos()) * factor + currentLocation.getyPos();
        return new ExactPosition((int) xPos,(int) yPos);
    }
    
    public static ExactPosition moveToLeftGoal(double speed, ExactPosition currentLocation){
        return getPosBySpeed(speed, currentLocation, LEFT_GOAL_POSITION);
    }
    
    public static ExactPosition moveToRightGoal(double speed, ExactPosition currentLocation){
        return getPosBySpeed(speed, currentLocation, RIGHT_GOAL_POSITION);
    }
}
