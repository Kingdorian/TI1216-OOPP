/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.CalculateMatch;

import application.animation.ContainerPackage.CurrentPositions;
import application.animation.ContainerPackage.ExactPosition;
import java.util.ArrayList;

/**
 * This class contains functions which each kind of player regularly uses
 *
 * @author faris
 */
public abstract class PlayerAI {

    public final double RUNNING_SPEED; // ~10
    public static final double WITH_BALL_SPEED = 4;
    public static final double WALK_SPEED = 6;
    public static final ExactPosition LEFT_GOAL_POSITION = new ExactPosition(60, 381);
    public static final ExactPosition RIGHT_GOAL_POSITION = new ExactPosition(955, 381);
    public static final int GOAL_SIZE = 80;
    public static final int MIDDLE_LINE_X = 510;

    public final CurrentPositions positions;

    public abstract ExactPosition getNextPosition();

    public PlayerAI(int id, boolean isOnAllyTeam, CurrentPositions positions) {
        this.positions = positions;
        if (id == 0) {
            //this player is the keeper
            RUNNING_SPEED = 10;
        } else if (isOnAllyTeam) {
            RUNNING_SPEED = 7 + positions.getAllyInfo(id).getStamina() / 20;
        } else {
            RUNNING_SPEED = 7 + positions.getEnemyInfo(id).getStamina() / 20;
        }
    }

    /**
     * Decide the position to move to, based on a direction, a current position
     * and a speed
     *
     * @param speed double: the speed
     * @param currentLocation ExactPosition: the current location of the player
     * @param direction ExactPosition: the location to move toward
     * @return the position to move to in 1 time slice
     */
    public static ExactPosition getPosBySpeed(double speed, ExactPosition currentLocation, ExactPosition direction) {
        if (currentLocation.distanceTo(direction) > 10) {
            double factor = speed / currentLocation.distanceTo(direction);

            double xPos = (direction.getxPos() - currentLocation.getxPos()) * factor + currentLocation.getxPos();
            double yPos = (direction.getyPos() - currentLocation.getyPos()) * factor + currentLocation.getyPos();
            return new ExactPosition((int) xPos, (int) yPos);
        } else {
            return direction;
        }
    }

    public static boolean enoughLuckToDefendBall(ExactPosition thisPlayer, int playerID, CurrentPositions positions, boolean isOnAllyTeam) {
        // if very close, return true, to avoid bugs
        if (thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 10) {
            return true;
        }
        ExactPosition closest;
        if (isOnAllyTeam) {
            closest = positions.getClosestAllyTo(BallAI.getCurrentBallPosition());
        } else {
            closest = positions.getClosestEnemyTo(BallAI.getCurrentBallPosition());
        }
        double opponentDistance = closest.distanceTo(BallAI.getCurrentBallPosition());
        double ownDistance = thisPlayer.distanceTo(BallAI.getCurrentBallPosition());
        int opponentID = positions.getIDByPosition(closest);
        double opponentAttack;
        double thisDefensePower;
        if (isOnAllyTeam) {
            opponentAttack = positions.getEnemyInfo(opponentID).getAttackPower();
            thisDefensePower = positions.getAllyInfo(playerID).getDefensePower();
        } else {
            opponentAttack = positions.getAllyInfo(opponentID).getAttackPower();
            thisDefensePower = positions.getEnemyInfo(playerID).getDefensePower();
        }
        double chance;
        //if(opponentDistance != 0 && ownDistance != 0)
        chance = ((ownDistance + 10) / thisDefensePower) / ((opponentDistance + 10) / opponentAttack);
//        else if(opponentDistance == 0)
//            chance = 0.001;
//        else
//            chance = 1000;

        // if player got enough luck
        return chance * Math.random() < Math.random() / chance;

    }

    public static boolean enoughLuckToShootBall(ExactPosition thisPlayer, int playerID, CurrentPositions positions, boolean isOnAllyTeam) {

        // if very close, return true, to avoid bugs
        if (thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 10) {
            return true;
        }

        ExactPosition closest;
        if (isOnAllyTeam) {
            closest = positions.getClosestAllyTo(BallAI.getCurrentBallPosition());
        } else {
            closest = positions.getClosestEnemyTo(BallAI.getCurrentBallPosition());
        }
        double opponentDistance = closest.distanceTo(BallAI.getCurrentBallPosition());
        double ownDistance = thisPlayer.distanceTo(BallAI.getCurrentBallPosition());
        int opponentID = positions.getIDByPosition(closest);
        double opponentDefense;
        double thisAttackPower;
        if (isOnAllyTeam) {
            opponentDefense = positions.getEnemyInfo(opponentID).getDefensePower();
            thisAttackPower = positions.getAllyInfo(playerID).getAttackPower();
        } else {
            opponentDefense = positions.getAllyInfo(opponentID).getDefensePower();
            thisAttackPower = positions.getEnemyInfo(playerID).getAttackPower();
        }
        double chance;
//        if(opponentDistance != 0 && ownDistance != 0)
        chance = ((ownDistance + 10) / thisAttackPower) / ((opponentDistance + 10) / opponentDefense);
//        else if(opponentDistance == 0)
//            chance = 0.001;
//        else
//            chance = 1000;

        // if player got enough luck
        return chance * Math.random() < Math.random() / chance;
    }

    public static ExactPosition[] get2ClosestPlayers(ExactPosition thisPlayer, CurrentPositions positions) {

        ArrayList<ExactPosition> opponents = positions.getOpponentsInFrontOf(thisPlayer);
        if (opponents.size() > 0) {
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
        } else {
            return null;
        }
    }

    public static ExactPosition getDirectionInBetween(ExactPosition thisPlayer, ExactPosition opponent1, ExactPosition opponent2) {

        double xDirection = opponent1.getxPos() - thisPlayer.getxPos() + opponent2.getxPos();
        double yDirection = opponent1.getyPos() - thisPlayer.getyPos() + opponent2.getyPos();
        ExactPosition direction = new ExactPosition(xDirection, yDirection);

        return direction;
    }

    public ExactPosition moveToLeftGoal(ExactPosition thisPlayer, double speed) {
        return getPosBySpeed(speed, thisPlayer, LEFT_GOAL_POSITION);
    }

    public ExactPosition moveToRightGoal(ExactPosition thisPlayer, double speed) {
        return getPosBySpeed(speed, thisPlayer, RIGHT_GOAL_POSITION);
    }

    /**
     * Check if this player should shoot the ball horizontally toward the
     * opponents goal
     *
     * @param thisPlayer this players position
     * @param positions the CurrentPositions instance describing where all the
     * players are right now
     * @param isOnAllyTeam if this player is on the ally team
     * @return true if the player should shoot horizontally
     */
    public boolean shootHorizontally(ExactPosition thisPlayer, CurrentPositions positions, boolean isOnAllyTeam) {

        ExactPosition ballDirection;
        ExactPosition closestOpponent;

        if (isOnAllyTeam) {
            ballDirection = thisPlayer.getTranslateX(40);
            closestOpponent = positions.getClosestEnemyTo(ballDirection);
        } else {
            ballDirection = thisPlayer.getTranslateX(-40);
            closestOpponent = positions.getClosestAllyTo(ballDirection);
        }

        // apply some randomness
        return closestOpponent.distanceTo(ballDirection) * Math.random() < 60 * Math.random();

    }
}
