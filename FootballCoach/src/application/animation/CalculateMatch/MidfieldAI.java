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
public class MidfieldAI extends PlayerAI {

    private final ExactPosition thisPlayer;
    private final boolean isOnAllyTeam;
    private final int playerID;

    /**
     * constructor: needs the position of the attacker and the positions of
     * other players
     *
     * @param thisPlayer the position of this attacker
     * @param positions the previous positions of all other players
     * @param isOnAllyTeam boolean conaining if this player is on the ally team
     * @param playerID
     */
    public MidfieldAI(ExactPosition thisPlayer, CurrentPositions positions, boolean isOnAllyTeam, int playerID) {
        super(playerID, isOnAllyTeam, positions);
        this.thisPlayer = thisPlayer;
        this.isOnAllyTeam = isOnAllyTeam;
        this.playerID = playerID;

    }

    @Override
    public ExactPosition getNextPosition() {
        if (isOnAllyTeam) {
            if (thisPlayer.equals(positions.getClosestAllyTo(BallAI.getCurrentBallPosition()))) // if closest player of own team to the ball
            {
                return moveTowardBall(); // closest to ball
            } else if (BallAI.getCurrentBallPosition().getxPos() > MIDDLE_LINE_X + 100) {
                return supportAttack(); // support attack
            } else if (BallAI.getCurrentBallPosition().getxPos() < MIDDLE_LINE_X - 100) {
                return supportDefense(); // support defense
            } else {
                return midfield(); // ball close to middle line: go to it
            }
        } else if (thisPlayer.equals(positions.getClosestEnemyTo(BallAI.getCurrentBallPosition()))) // if closest player of own team to the ball
        {
            return moveTowardBall(); // closest to ball
        } else if (BallAI.getCurrentBallPosition().getxPos() < MIDDLE_LINE_X - 100) {
            return supportAttack(); // support attack
        } else if (BallAI.getCurrentBallPosition().getxPos() > MIDDLE_LINE_X + 100) {
            return supportDefense(); // support defense
        } else {
            return midfield(); // ball close to middle line: go to it
        }
    }

    // Ball close to this player, go after it.
    private ExactPosition moveTowardBall() {
        if (positions.isClosestToBall(thisPlayer) && thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 25) {
            // shoot at goal, if close to goal
            if ((!isOnAllyTeam) && thisPlayer.distanceTo(LEFT_GOAL_POSITION) < 150) {
                if (enoughLuckToShootBall(thisPlayer, playerID, positions, isOnAllyTeam)) {
                    BallAI.shootLeftGoal(playerID);
                }
            } else if (isOnAllyTeam && thisPlayer.distanceTo(RIGHT_GOAL_POSITION) < 150) {
                if (enoughLuckToShootBall(thisPlayer, playerID, positions, isOnAllyTeam)) {
                    BallAI.shootRightGoal(playerID);
                }
            } else if ((isOnAllyTeam && thisPlayer.getxPos() < MIDDLE_LINE_X) || (!isOnAllyTeam && thisPlayer.getxPos() > MIDDLE_LINE_X)) {
                if (enoughLuckToDefendBall(thisPlayer, playerID, positions, isOnAllyTeam)) {
                    //defend ball

                    //1. check if any player in front of the midfielder is reachable and not being defended
                    ExactPosition target = null;
                    if (isOnAllyTeam) {
                        for (ExactPosition player : positions.getAllyTeam()) {
                            if (player.getxPos() > thisPlayer.getxPos() && positions.getClosestEnemyTo(player).distanceTo(player) > 50 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target) && thisPlayer.distanceTo(player) < 250) {
                                target = player;
                            }
                        }
                    } else {
                        for (ExactPosition player : positions.getEnemyTeam()) {
                            if (player.getxPos() < thisPlayer.getxPos() && positions.getClosestAllyTo(player).distanceTo(player) > 50 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target) && thisPlayer.distanceTo(player) < 250) {
                                target = player;
                            }
                        }
                    }

                    if (target != null) {
                        BallAI.shootToTeammate(target, isOnAllyTeam); // shoot ball to closest ally who's not being defended
                    } //2. else check if you can move forward yourself
                    // check if you should shoot the ball horizontally
                    else if (shootHorizontally(thisPlayer, positions, isOnAllyTeam)) {
                        if (isOnAllyTeam) {
                            BallAI.shootBallTo(thisPlayer.getTranslateX(40), isOnAllyTeam);
                            return getPosBySpeed(WITH_BALL_SPEED, thisPlayer, thisPlayer.getTranslateX(40));
                        } else {
                            BallAI.shootBallTo(thisPlayer.getTranslateX(-40), isOnAllyTeam);
                            return getPosBySpeed(WITH_BALL_SPEED, thisPlayer, thisPlayer.getTranslateX(-40));
                        }
                    } // else check if you should shoot through the center of the closest 2 opponents
                    else if ((isOnAllyTeam && positions.getClosestEnemyTo(thisPlayer).distanceTo(thisPlayer) > 100) || (!isOnAllyTeam && positions.getClosestAllyTo(thisPlayer).distanceTo(thisPlayer) < 100)) {
                        ExactPosition closestArr[] = get2ClosestPlayers(thisPlayer, positions);
                        if (closestArr != null && closestArr.length > 1 && closestArr[1] != null) {
                            ExactPosition direction = getDirectionInBetween(thisPlayer, closestArr[0], closestArr[1]);
                            BallAI.shootBallTo(direction, isOnAllyTeam);
                        } // if no opponent in front of midfielder, shoot toward goal
                        else if (isOnAllyTeam) {
                            // shoot ball and walk to right goal
                            BallAI.shootBallTo(RIGHT_GOAL_POSITION, isOnAllyTeam);
                            return moveToRightGoal(thisPlayer, WITH_BALL_SPEED);
                        } else {
                            // shoot ball and walk to left goal
                            BallAI.shootBallTo(LEFT_GOAL_POSITION, isOnAllyTeam);
                            return moveToLeftGoal(thisPlayer, WITH_BALL_SPEED);
                        }
                    } //3. else shoot to any ally not being defended
                    else {
                        if (isOnAllyTeam) {
                            for (ExactPosition player : positions.getAllyTeam()) {
                                if (positions.getClosestEnemyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target)) {
                                    target = player;
                                }
                            }
                        } else {
                            for (ExactPosition player : positions.getEnemyTeam()) {
                                if (positions.getClosestAllyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target)) {
                                    target = player;
                                }
                            }
                        }
                        if (target != null) {
                            BallAI.shootToTeammate(target, isOnAllyTeam); // shoot ball to closest ally who's not being defended
                        } //4. else shoot toward goal
                        else {
                            if (isOnAllyTeam) {
                                // shoot ball and walk to right goal
                                BallAI.shootBallTo(RIGHT_GOAL_POSITION, isOnAllyTeam);
                                return moveToRightGoal(thisPlayer, WITH_BALL_SPEED);
                            } else {
                                // shoot ball and walk to left goal
                                BallAI.shootBallTo(LEFT_GOAL_POSITION, isOnAllyTeam);
                                return moveToLeftGoal(thisPlayer, WITH_BALL_SPEED);
                            }
                        }
                    }
                }
            } else {
                if (enoughLuckToShootBall(thisPlayer, playerID, positions, isOnAllyTeam)) {
                    //attack ball

                    //1. check if any player in front of the midfielder is reachable and not being defended
                    ExactPosition target = null;
                    if (isOnAllyTeam) {
                        for (ExactPosition player : positions.getAllyTeam()) {
                            if (player.getxPos() > thisPlayer.getxPos() && positions.getClosestEnemyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target) && thisPlayer.distanceTo(player) < 250) {
                                target = player;
                            }
                        }
                    } else {
                        for (ExactPosition player : positions.getEnemyTeam()) {
                            if (player.getxPos() < thisPlayer.getxPos() && positions.getClosestAllyTo(player).distanceTo(player) > 100 && thisPlayer.distanceTo(player) < thisPlayer.distanceTo(target) && thisPlayer.distanceTo(player) < 250) {
                                target = player;
                            }
                        }
                    }

                    if (target != null) {
                        BallAI.shootToTeammate(target, isOnAllyTeam); // shoot ball to closest ally who's not being defended
                    } //2. else move forward yourself
                    else {
                        ExactPosition closestArr[] = get2ClosestPlayers(thisPlayer, positions);
                        if (closestArr != null && closestArr.length > 1 && closestArr[1] != null) {
                            ExactPosition direction = getDirectionInBetween(thisPlayer, closestArr[0], closestArr[1]);
                            BallAI.shootBallTo(direction, isOnAllyTeam);
                        } // if no opponents in front of you, shoot toward goal
                        else if (isOnAllyTeam) {
                            // shoot ball and walk to right goal
                            BallAI.shootBallTo(RIGHT_GOAL_POSITION, isOnAllyTeam);
                            return moveToRightGoal(thisPlayer, WITH_BALL_SPEED);
                        } else {
                            // shoot ball and walk to left goal
                            BallAI.shootBallTo(LEFT_GOAL_POSITION, isOnAllyTeam);
                            return moveToLeftGoal(thisPlayer, WITH_BALL_SPEED);
                        }
                    }
                }
            }
        }
        // move toward ball
        return getPosBySpeed(RUNNING_SPEED, thisPlayer, BallAI.getCurrentBallPosition());
    }

    // Ball far from middle line: support defense (create counters)
    private ExactPosition supportDefense() {
        if (isOnAllyTeam) {
            return getPosBySpeed(WALK_SPEED, thisPlayer, positions.getAllyInfo(playerID).getFavoritePosition().getTranslateX(-100));
        } else {
            return getPosBySpeed(WALK_SPEED, thisPlayer, positions.getEnemyInfo(playerID).getFavoritePosition().getTranslateX(100));
        }
    }

    // Ball far from middle line: support attack (prevent counters)
    private ExactPosition supportAttack() {
        if (isOnAllyTeam) {
            return getPosBySpeed(WALK_SPEED, thisPlayer, positions.getAllyInfo(playerID).getFavoritePosition()/*.getTranslateX(50)*/);
        } else {
            return getPosBySpeed(WALK_SPEED, thisPlayer, positions.getEnemyInfo(playerID).getFavoritePosition()/*.getTranslateX(-50)*/);
        }
    }

    // Ball close to middle line: try to get ball (stay close to ball)
    private ExactPosition midfield() {
        if (isOnAllyTeam) {
            double xPos = BallAI.getCurrentBallPosition().getxPos() - 15;
            double yPos = positions.getAllyInfo(playerID).getFavoritePosition().getyPos();
            return getPosBySpeed(RUNNING_SPEED, thisPlayer, new ExactPosition(xPos, yPos));
        } else {
            double xPos = BallAI.getCurrentBallPosition().getxPos() + 15;
            double yPos = positions.getEnemyInfo(playerID).getFavoritePosition().getyPos();
            return getPosBySpeed(RUNNING_SPEED, thisPlayer, new ExactPosition(xPos, yPos));
        }
    }

}
