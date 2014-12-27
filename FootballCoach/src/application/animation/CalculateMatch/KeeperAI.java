/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.CalculateMatch;

import application.animation.ContainerPackage.CurrentPositions;
import application.animation.ContainerPackage.ExactPosition;

/**
 *
 * @author faris
 */
public class KeeperAI extends PlayerAI {

    private final ExactPosition thisPlayer;
    private final boolean isOnAllyTeam;
    private final int playerID;

    private final int STOP_LUCK = 140; //make higher to stop ball more often

    /**
     * constructor: needs the position of the attacker and the positions of
     * other players
     *
     * @param thisPlayer the position of this attacker
     * @param positions the previous positions of all other players
     * @param isOnAllyTeam boolean conaining if this player is on the ally team
     * @param playerID
     */
    public KeeperAI(ExactPosition thisPlayer, CurrentPositions positions, boolean isOnAllyTeam, int playerID) {
        super(playerID, isOnAllyTeam, positions);
        this.thisPlayer = thisPlayer;
        this.isOnAllyTeam = isOnAllyTeam;
        this.playerID = playerID;
    }

    /**
     * gives the position the attacker want to move to
     *
     * @return the ExactPosition the attacker want to move to
     */
    @Override
    public ExactPosition getNextPosition() {
        // if ball far away, go to favorite position.
        if (thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) > 300) {
            return ballFarAway();
        } // if ball very close, try to kick ball away
        else if (thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 150) {
            return stopBall();
        } // if ball not very close, but also not far away, move toward the side where the ball is
        else {
            return ballClose();
        }
    }

    // Stay in center of goal
    private ExactPosition ballFarAway() {
        if (isOnAllyTeam) {
            return getPosBySpeed(WALK_SPEED, thisPlayer, positions.getAllyInfo(playerID).getFavoritePosition());
        } else {
            return getPosBySpeed(WALK_SPEED, thisPlayer, positions.getEnemyInfo(playerID).getFavoritePosition());
        }
    }

    // Ball close to goal: go toward y pos of ball
    private ExactPosition ballClose() {
        ExactPosition favoritePosition;
        if (isOnAllyTeam) {
            favoritePosition = positions.getAllyInfo(playerID).getFavoritePosition();
        } else {
            favoritePosition = positions.getEnemyInfo(playerID).getFavoritePosition();
        }

        double yOffSet;
        ExactPosition ballPosition = BallAI.getCurrentBallPosition();
        if (ballPosition.getyPos() < favoritePosition.getyPos() - 40) {
            yOffSet = -40;
        } else if (ballPosition.getyPos() > favoritePosition.getyPos() + 40) {
            yOffSet = 40;
        } else {
            yOffSet = (ballPosition.getyPos() - favoritePosition.getyPos());
        }
        ExactPosition direction = new ExactPosition(favoritePosition.getxPos(), favoritePosition.getyPos() + yOffSet);

        return getPosBySpeed(WALK_SPEED, thisPlayer, direction);
    }

    // Ball shot at goal: stop ball based on luck and skills
    private ExactPosition stopBall() {

        double stopPower = positions.getAllyInfo(playerID).getStopPower();
        double penaltyStopPower = positions.getAllyInfo(playerID).getPenaltyStopPower();
        double ballSpeed = BallAI.getBallSpeed();

        if (BallAI.isNextFrameOutsideField()) {
            // try to stop ball + 'jump' to it, if within distance of 30 pixels
            ExactPosition intersectionPoint = BallAI.getGoalIntersection(isOnAllyTeam);
            if (thisPlayer.distanceTo(intersectionPoint) < 70) {
                BallAI.stopBall(intersectionPoint, isOnAllyTeam);
                return intersectionPoint;
            }
        } else if (ballSpeed < 5 && thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 25) {
            // shoot ball
            ExactPosition target = getTarget();
            if (target != null) {
                BallAI.shootToTeammate(target, isOnAllyTeam); // get close ally who isn't close to an opponent
            } else {
                BallAI.shootToTeammate(thisPlayer.getTranslateX(isOnAllyTeam ? 400 : -400), isOnAllyTeam);
            }
        } else if ((isOnAllyTeam && BallAI.isMovingTowardLeftGoal(true)) || (!isOnAllyTeam && BallAI.isMovingTowardRightGoal(true))) {
            // run to where ball will go in goal
            return getPosBySpeed(RUNNING_SPEED, thisPlayer, BallAI.getGoalIntersection(isOnAllyTeam));
        } else {
            // walk toward the side where the ball is
            return ballClose();
        }

//            // if ball has been stopped, shoot it to an ally
//            if(ballSpeed < 5 && thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 25){
//                ExactPosition target = getTarget();
//                if(target != null)
//                    BallAI.shootToTeammate(target, isOnAllyTeam); // get close ally who isn't close to an opponent
//                else
//                    BallAI.shootToTeammate(thisPlayer.getTranslateX(isOnAllyTeam?400:-400), isOnAllyTeam);
//            }
//            // if ball has not been stopped and will move into the goal if not stopped, try to stop it
//            else if((isOnAllyTeam && BallAI.isMovingTowardLeftGoal(true)) || (!isOnAllyTeam && BallAI.isMovingTowardRightGoal(true))){
//                double stopChance = STOP_LUCK;
//                stopChance  /= ballSpeed/(stopPower);
//                stopChance *= thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 25 ? 3 : 1;
//                if(Math.random() * stopChance > Math.random() * 30)
//                    BallAI.stopBall(thisPlayer, isOnAllyTeam);
//            } else
//                return ballClose();
        return thisPlayer;
    }

    private ExactPosition getTarget() {
        ExactPosition target = null;
        if (isOnAllyTeam) {
            for (ExactPosition p : positions.getAllyTeam()) {
                if (thisPlayer.distanceTo(p) > 100 && thisPlayer.distanceTo(p) < thisPlayer.distanceTo(target) && positions.getClosestEnemyInFrontOf(p).distanceTo(p) > 100) {
                    target = p;
                }
            }
        } else {
            for (ExactPosition p : positions.getEnemyTeam()) {
                if (thisPlayer.distanceTo(p) > 100 && thisPlayer.distanceTo(p) < thisPlayer.distanceTo(target) && positions.getClosestAllyInFrontOf(p).distanceTo(p) > 100 && thisPlayer.distanceTo(p) < 400) {
                    target = p;
                }
            }
        }
        if (target != null) {
            BallAI.shootToTeammate(target, isOnAllyTeam); // get close ally who isn't close to an opponent
        } else {
            BallAI.shootToTeammate(thisPlayer.getTranslateX(isOnAllyTeam ? 400 : -400), isOnAllyTeam);
        }
        return target;
    }
}
