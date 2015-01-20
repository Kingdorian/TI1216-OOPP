/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.CalculateMatch;

import application.animation.Container.CurrentPositions;
import application.animation.Container.Position;
import java.util.ArrayList;

/**
 * This class contains functions which each kind of player regularly uses
 *
 * @author Faris
 */
public abstract class PlayerAI {

    protected final double RUNNING_SPEED; // ~10
    protected static final double WITH_BALL_SPEED = 4;
    protected static final double WALK_SPEED = 6;
    protected static final Position LEFT_GOAL_POSITION = new Position(60, 381);
    protected static final Position RIGHT_GOAL_POSITION = new Position(955, 381);
    protected static final int GOAL_SIZE = 80;
    protected static final int MIDDLE_LINE_X = 510;

    protected final CurrentPositions positions;

    protected abstract Position getNextPosition();

    /**
     * Constructor
     *
     * @param id players id
     * @param isOnAllyTeam if the player is on the ally team
     * @param positions the current positions of all players
     */
    protected PlayerAI(int id, boolean isOnAllyTeam, CurrentPositions positions) {
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
    protected static Position getPosBySpeed(double speed, Position currentLocation, Position direction) {
        if (currentLocation.distanceTo(direction) > 10) {
            double factor = speed / currentLocation.distanceTo(direction);

            double xPos = (direction.getxPos() - currentLocation.getxPos()) * factor + currentLocation.getxPos();
            double yPos = (direction.getyPos() - currentLocation.getyPos()) * factor + currentLocation.getyPos();
            return new Position((int) xPos, (int) yPos);
        } else {
            return direction;
        }
    }

    /**
     * Calculate if the player has enough luck to defend the ball.
     *
     * @param thisPlayer the position of this player
     * @param playerID the id of this player
     * @param positions the positions of the other players
     * @param isOnAllyTeam if the player is on the ally team
     * @return if the player got enough luck
     */
    protected static boolean enoughLuckToDefendBall(Position thisPlayer, int playerID, CurrentPositions positions, boolean isOnAllyTeam) {
        // if very close, return true, to avoid bugs
        if (thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 10) {
            return true;
        }
        Position closest;
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
        chance = ((ownDistance + 10) / thisDefensePower) / ((opponentDistance + 10) / opponentAttack);

        // if player got enough luck
        return chance * Math.random() < Math.random() / chance;

    }

    /**
     * Calculate if the player has enough luck to shoot the ball.
     *
     * @param thisPlayer the position of this player
     * @param playerID the id of this player
     * @param positions the positions of the other players
     * @param isOnAllyTeam if the player is on the ally team
     * @return if the player got enough luck
     */
    protected static boolean enoughLuckToShootBall(Position thisPlayer, int playerID, CurrentPositions positions, boolean isOnAllyTeam) {

        // if very close, return true, to avoid bugs
        if (thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 10) {
            return true;
        }

        Position closest;
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
        chance = ((ownDistance + 10) / thisAttackPower) / ((opponentDistance + 10) / opponentDefense);

        // if player got enough luck
        return chance * Math.random() < Math.random() / chance;
    }

    /**
     * Get the 2 closest opponents
     *
     * @param thisPlayer this player
     * @param positions the positions of the other players
     * @param isOnAllyTeam if the player is on the ally team
     * @return array containing the two closest players
     */
    protected static Position[] get2ClosestOpponents(Position thisPlayer, CurrentPositions positions, boolean isOnAllyTeam) {

        ArrayList<Position> opponents = positions.getOpponentsInFrontOf(thisPlayer, isOnAllyTeam);
        if (opponents.size() > 0) {
            // get the closest opponent to the player
            Position closest = opponents.get(0);
            for (int i = 1; i < opponents.size(); i++) {
                if (thisPlayer.distanceTo(opponents.get(i)) < thisPlayer.distanceTo(closest)) {
                    closest = opponents.get(i);
                }
            }

            // get the second closest opponent to the player
            Position secondClosest = null;
            for (Position p : opponents) {
                if (thisPlayer.distanceTo(p) > thisPlayer.distanceTo(closest) && thisPlayer.distanceTo(p) < thisPlayer.distanceTo(secondClosest) && Math.abs(p.getyPos() - closest.getyPos()) > 60) {
                    secondClosest = p;
                }
            }
            Position result[] = {closest, secondClosest};
            return result;
        } else {
            return null;
        }
    }

    /**
     * Get the position through the middle of opponent1 and 2 from thisPlayer
     *
     * @param thisPlayer this player
     * @param opponent1 first opponent
     * @param opponent2 second opponent
     * @return the position through the middle of opponent1 and 2 from
     * thisPlayer
     */
    protected static Position getDirectionInBetween(Position thisPlayer, Position opponent1, Position opponent2) {

        double xDirection = opponent1.getxPos() - thisPlayer.getxPos() + opponent2.getxPos();
        double yDirection = opponent1.getyPos() - thisPlayer.getyPos() + opponent2.getyPos();
        Position direction = new Position(xDirection, yDirection);

        return direction;
    }

    /**
     * Get the position to move to if the player want to move to the left goal
     *
     * @param thisPlayer this player
     * @param speed the speed to move at
     * @return the position to move to
     */
    protected static Position moveToLeftGoal(Position thisPlayer, double speed) {
        return getPosBySpeed(speed, thisPlayer, LEFT_GOAL_POSITION);
    }

    /**
     * Get the position to move to if the player want to move to the left goal
     *
     * @param thisPlayer this player
     * @param speed the speed to move at
     * @return the position to move to
     */
    protected static Position moveToRightGoal(Position thisPlayer, double speed) {
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
    protected static boolean shootHorizontally(Position thisPlayer, CurrentPositions positions, boolean isOnAllyTeam) {

        Position ballDirection;
        Position closestOpponent;

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
