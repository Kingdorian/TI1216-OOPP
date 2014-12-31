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

    private final double STOP_LUCK = 1.4; //make higher to stop ball more often (between 1 and 2 should be fine)

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
    private static int goalAllyGoal = 0;
    private static int goalEnemyGoal = 0;

    // Ball shot at goal: stop ball based on luck and skills

    private ExactPosition stopBall() {

        double ballSpeed = BallAI.getBallSpeed();

        if (BallAI.isNextFrameOutsideField()) {
            // try to stop ball + 'jump' to it, if within distance of 30 pixels
            ExactPosition intersectionPoint = BallAI.getGoalIntersection(isOnAllyTeam);
            if (thisPlayer.distanceTo(intersectionPoint) < 70) {
                if (willStopBall(ballSpeed)) {
                    BallAI.stopBall(intersectionPoint, isOnAllyTeam);
                } else {
                    if (isOnAllyTeam) {
                        goalAllyGoal++;
                    } else {
                        goalEnemyGoal++;
                    }
                }
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
        return thisPlayer;
    }

    private boolean willStopBall(double ballSpeed) {

        if (ballSpeed < 20) // always stop slow balls
        {
            return true;
        }

        double stopPower = positions.getAllyInfo(playerID).getStopPower();
        double penaltyStopPower = positions.getAllyInfo(playerID).getPenaltyStopPower();

        // first calculate the stop power, also use allies defending power 
        // of the closest ally to the attacker (to make defending power more important)
        ExactPosition attacker;
        ExactPosition closestDefender;
        if (isOnAllyTeam) {
            attacker = positions.getClosestEnemyTo(thisPlayer);
            closestDefender = positions.getClosestAllyTo(attacker);
            if (attacker.distanceTo(closestDefender) > 40) {
                closestDefender = null;
            }
        } else {
            attacker = positions.getClosestAllyTo(thisPlayer);
            closestDefender = positions.getClosestEnemyTo(attacker);
            if (attacker.distanceTo(closestDefender) > 40) {
                closestDefender = null;
            }
        }
        if (closestDefender != null) {
            if (isOnAllyTeam) {
                stopPower += CurrentPositions.getAllyInfo(positions.getIDByPosition(closestDefender)).getDefensePower() / 2.0;
            } else {
                stopPower += CurrentPositions.getEnemyInfo(positions.getIDByPosition(closestDefender)).getDefensePower() / 2.0;
            }
        }
        stopPower += penaltyStopPower / 5.0;
        // now the perfect stopPower is 170, 
        
        // rescale it to 0-100 (with closer to perfect giving a value closer to 100
        stopPower = Math.pow(stopPower, 0.896); 
        
        // good keeper+good close defender (power ~80) = ~73
        // good keeper (80), no defender = 50
        // bad keeper (60), no defender = 40
        
        return Math.pow(stopPower, STOP_LUCK) * Math.random() > (ballSpeed) * Math.random();
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
