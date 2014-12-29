package application.animation.CalculateMatch;


import application.animation.ContainerPackage.CurrentPositions;
import application.animation.ContainerPackage.ExactPosition;
import java.util.ArrayList;

/**
 * This class can be used to calculate the next position an attacker should move
 * to.
 *
 * @author faris
 */
public class AttackerAI extends PlayerAI {

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
    public AttackerAI(ExactPosition thisPlayer, CurrentPositions positions, boolean isOnAllyTeam, int playerID) {
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
        
        if (isOnAllyTeam) {
            if (thisPlayer.equals(positions.getClosestAllyTo(BallAI.getCurrentBallPosition()))) // if closest player of own team to the ball
            {
                return moveTowardBall();
            } else if (BallAI.getCurrentBallPosition().getxPos() > MIDDLE_LINE_X) {
                return attackingPlayer();
            } else {
                return defendingPlayer();
            }
        } else if (thisPlayer.equals(positions.getClosestEnemyTo(BallAI.getCurrentBallPosition()))) // if closest player of own team to the ball
        {
            return moveTowardBall();
        } else if (BallAI.getCurrentBallPosition().getxPos() < MIDDLE_LINE_X) {
            return attackingPlayer();
        } else {
            return defendingPlayer();
        }
    }

    /**
     * this player is the closest player to the ball, so move
     * toward it
     */
    private ExactPosition moveTowardBall() {
        // if closest to the ball and distance to ball is smaller than 25, close enough to shoot
        if (positions.isClosestToBall(thisPlayer) && thisPlayer.distanceTo(BallAI.getCurrentBallPosition()) < 25) {
            // shoot the goal, if close enough
            if ((!isOnAllyTeam) && thisPlayer.distanceTo(LEFT_GOAL_POSITION) < 200) {
                if (enoughLuckToShootBall(thisPlayer, playerID, positions, isOnAllyTeam)) {
                    BallAI.shootLeftGoal(playerID);
                }
            } else if (isOnAllyTeam && thisPlayer.distanceTo(RIGHT_GOAL_POSITION) < 200) {
                if (enoughLuckToShootBall(thisPlayer, playerID, positions, isOnAllyTeam)) {
                    BallAI.shootRightGoal(playerID);
                }
            } else {
                // close enough to ball: decide direction to kick ball
                ArrayList<ExactPosition> opponents = positions.getOpponentsInFrontOf(thisPlayer);

                if (opponents.size() > 1) {
                    // check if should shoot horitzontally
                    if (shootHorizontally(thisPlayer, positions, isOnAllyTeam)){
                        if(isOnAllyTeam){
                            BallAI.shootBallTo(thisPlayer.getTranslateX(40), isOnAllyTeam);
                            return getPosBySpeed(WITH_BALL_SPEED, thisPlayer, thisPlayer.getTranslateX(40));
                        } else{
                            BallAI.shootBallTo(thisPlayer.getTranslateX(-40), isOnAllyTeam);
                            return getPosBySpeed(WITH_BALL_SPEED, thisPlayer, thisPlayer.getTranslateX(-40));
                        }
                    } else{
                        // else check if should shoot through the center of the two closest opponents
                        // get the closest opponent to the player
                        ExactPosition closestArr[] = get2ClosestPlayers(thisPlayer, positions);
                        ExactPosition closest = null;
                        ExactPosition secondClosest = null;
                        if(closestArr != null){
                            closest = closestArr[0];
                            if(closestArr.length > 1)
                                secondClosest = closestArr[1];
                        }

                        if (secondClosest != null) {
                            // get direction to shoot ball to
                            ExactPosition direction = getDirectionInBetween(thisPlayer, closest, secondClosest);

                            // if player got enough luck
                            if (enoughLuckToShootBall(thisPlayer, playerID, positions, isOnAllyTeam)) {

                                // if there is an opponent very close, decide to shoot the ball to an ally, 
                                // based on how close the opponent is. (closer = higher chance to shoot to an ally)
                                double distanceToClosest;
                                if (isOnAllyTeam) {
                                    distanceToClosest = positions.getClosestEnemyInFrontOf(thisPlayer).distanceTo(thisPlayer);
                                } else {
                                    distanceToClosest = positions.getClosestAllyInFrontOf(thisPlayer).distanceTo(thisPlayer);
                                }

                                if (distanceToClosest < 100 && distanceToClosest * Math.random() < 70 * Math.random()) {
                                    ExactPosition closestOfOwnTeam = null;
                                    if (isOnAllyTeam) {
                                        for (ExactPosition pp : positions.getAllyTeam()) {
                                            if (pp != thisPlayer && thisPlayer.distanceTo(pp) < thisPlayer.distanceTo(closestOfOwnTeam) && positions.getClosestEnemyInFrontOf(pp).distanceTo(pp) > 50 && thisPlayer.distanceTo(pp) < 150) {
                                                closestOfOwnTeam = pp;
                                            }
                                        }
                                    } else {
                                        for (ExactPosition pp : positions.getEnemyTeam()) {
                                            if (pp != thisPlayer && thisPlayer.distanceTo(pp) < thisPlayer.distanceTo(closestOfOwnTeam) && positions.getClosestAllyInFrontOf(pp).distanceTo(pp) > 50 && thisPlayer.distanceTo(pp) < 150) {
                                                closestOfOwnTeam = pp;
                                            }
                                        }
                                    }
                                    if (closestOfOwnTeam != null) {
                                        BallAI.shootToTeammate(closestOfOwnTeam, isOnAllyTeam);
                                        return getPosBySpeed(WITH_BALL_SPEED, thisPlayer, direction);
                                    }
                                }

                                // shoot ball
                                BallAI.shootBallTo(direction, isOnAllyTeam);

                                // move toward where you shot the ball to
                                return getPosBySpeed(WITH_BALL_SPEED, thisPlayer, direction);
                            }
                        }
                    }
                } else {
                    // if player got enough luck
                    if (enoughLuckToShootBall(thisPlayer, playerID, positions, isOnAllyTeam)) {
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
        }
        return getPosBySpeed(RUNNING_SPEED, thisPlayer, BallAI.getCurrentBallPosition());
    }

    /**
     * the player is not the closest of his team to the ball, but the ball is on
     * the opponents side, so help attacking
     */
    private ExactPosition attackingPlayer() {

        if ((BallAI.islastShotByAllyTeam() && isOnAllyTeam) || (!BallAI.islastShotByAllyTeam() && !isOnAllyTeam)) {
            ArrayList<ExactPosition> opponents = positions.getOpponentsInFrontOf(thisPlayer);
            if (opponents.size() > 2) {
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
                if (secondClosest != null) {
                    // set direction to x-pos of the ball and y-pos in the middle of the closest 2 opponents
                    double xDirection = BallAI.getCurrentBallPosition().getxPos();
                    double yDirection = closest.getyPos() - thisPlayer.getyPos() + secondClosest.getyPos();
                    ExactPosition direction = new ExactPosition(xDirection, yDirection);
                    return getPosBySpeed(RUNNING_SPEED, thisPlayer, direction);
                }
            } else {
                ExactPosition destination = new ExactPosition();
                destination.setxPos(BallAI.getCurrentBallPosition().getxPos());
                destination.setyPos((positions.getAllyInfo(playerID).getFavoritePosition().getyPos() * 2 + BallAI.getCurrentBallPosition().getyPos()) / 3);
                return getPosBySpeed(RUNNING_SPEED, thisPlayer, destination);
            }
        }

        return defaultPreferredDirection();
    }

    /**
     * The ball is on the attackers own side of the field, so go closer to the
     * middle line, to make it easier to counter
     */
    private ExactPosition defendingPlayer() {
        if (isOnAllyTeam) {
            return getPosBySpeed(WALK_SPEED, thisPlayer, positions.getAllyInfo(playerID).getFavoritePosition().getTranslateX(-100));
        } else
            return getPosBySpeed(WALK_SPEED, thisPlayer, positions.getEnemyInfo(playerID).getFavoritePosition().getTranslateX(100));
    }

    /**
     * The palyer doesn't have to do anything important, so get a default
     * direction for these kind of situations
     *
     * @return the position to move to
     */
    public ExactPosition defaultPreferredDirection() {
        ExactPosition destination = new ExactPosition();
        if (thisPlayer.getxPos() - 20 < BallAI.getCurrentBallPosition().getxPos() && thisPlayer.getxPos() + 20 > BallAI.getCurrentBallPosition().getxPos()) {
            destination.setxPos(BallAI.getCurrentBallPosition().getxPos());
        } else {
            destination.setxPos(thisPlayer.getxPos());
        }

        if (isOnAllyTeam) {
            if (destination.getxPos() < MIDDLE_LINE_X) {
                destination.setxPos(MIDDLE_LINE_X);
            }
            destination.setyPos(positions.getAllyInfo(playerID).getFavoritePosition().getyPos());
        } else {
            if (destination.getxPos() > MIDDLE_LINE_X) {
                destination.setxPos(MIDDLE_LINE_X);
            }
            destination.setyPos(positions.getEnemyInfo(playerID).getFavoritePosition().getyPos());
        }
        return getPosBySpeed(WALK_SPEED, thisPlayer, destination);
    }
    
}
