package application.animation.CalculateMatch;

import application.animation.Container.CurrentPositions;
import application.animation.Container.Position;
import application.animation.Container.Vector;

/**
 * This class decides the behaviour of the ball (so it can roll for more than 1
 * frame and slows down over time)
 *
 * @author Faris
 */
public class BallAI {

    private static final Position LEFT_GOAL_POSITION = PlayerAI.LEFT_GOAL_POSITION;
    private static final Position RIGHT_GOAL_POSITION = PlayerAI.RIGHT_GOAL_POSITION;
    private static final int GOAL_SIZE = PlayerAI.GOAL_SIZE;

    private static final double BALLSPEED = 20.0;

    private static Position currentBallPosition;
    private static Vector ballVector;
    private static int counter = 2;
    private static boolean shootHard = false;
    private static double shootHardSpeedMultiplier;
    private static boolean shootToTeammate = false;
    private static boolean lastShotByAllyTeam = false;
    private static boolean isOutsideOfField = false;
    private static boolean ballStopped = false;

    /**
     * Let only instances of this package make instances of this class
     */
    protected BallAI() {
    }

    /**
     * Set the direction for the ball to move to
     *
     * @param destination an ExactPosition containing the direction of the ball
     * @param ShotByAllyTeam true if the ball was shot by a palyer of the ally
     * team
     */
    protected static void shootBallTo(Position destination, boolean ShotByAllyTeam) {
        // if ball has not just been shot
        if (counter > 1) {
            lastShotByAllyTeam = ShotByAllyTeam;
            shootHard = false;
            shootToTeammate = false;
            ballVector = new Vector(currentBallPosition, destination);
            counter = 0;
        }
    }

    /**
     * Calculate the next location for the ball to move to
     *
     * @return ExactPosition containing the position the ball will move to next
     */
    public static Position getNextBallPosition() {

        //if ball is outside of field, return ball position and set outside of field variable to true
        isOutsideOfField = false;
        if (checkBallOutsideOfField()) {
            isOutsideOfField = true;
            return currentBallPosition;
        }

        // if ball was stopped by the keeper, move ball to keeper
        if (ballStopped) {
            ballStopped = false;
            return ballVector.getPointTo();
        }

        counter++;
        double speed = getBallSpeed();

        if (speed < 5) {
            return currentBallPosition;
        }

        if (ballVector != null) {
            currentBallPosition = ballVector.translate(currentBallPosition, speed);
        }

        return currentBallPosition;
    }

    /**
     * Checks if the ball is currently outside of the field
     *
     * @return if the ball is outside of the field
     */
    private static boolean checkBallOutsideOfField() {
        double xPos = currentBallPosition.getxPos();
        double yPos = currentBallPosition.getyPos();

        // check if the ball is outside the field
        if (xPos < 60) {
            if (isMovingTowardLeftGoal(false)) {
                CurrentPositions.addPointRight(); // left team scored
                return true;
            } else {
                return true;
            }
        } else if (xPos > 995) {
            if (isMovingTowardRightGoal(false)) {
                CurrentPositions.addPointLeft(); // right team scored
                return true;
            } else {
                return true;
            }
        } else {
            return yPos < 58 || yPos > 703;
        }
    }

    /**
     * Gives the current ball position
     *
     * @return an ExactPosition containing the current ball position
     */
    public static Position getCurrentBallPosition() {
        return currentBallPosition;
    }

    /**
     * Set the current position of the ball
     *
     * @param BallPosition an ExactPosition containing the currect location of
     * the ball
     */
    public static void setCurrentBallPosition(Position BallPosition) {
        currentBallPosition = BallPosition;
    }

    /**
     * Shoot the ball at the left goal with the speed based on the attack power
     * of the player
     *
     * @param playerID the players ID
     */
    protected static void shootLeftGoal(int playerID) {
        if (counter > 1) {
            // decide direction to shoot ball
            double xPos = LEFT_GOAL_POSITION.getxPos();
            double yPos = LEFT_GOAL_POSITION.getyPos();
            yPos += (Math.random() * GOAL_SIZE) - (Math.random() * GOAL_SIZE);

            // shoot ball
            shootBallTo(new Position(xPos, yPos), false);
            shootHard = true;

            //assume only players of enemy team will shoot the left goal
            int attackPower = CurrentPositions.getEnemyInfo(playerID).getAttackPower();
            shootHardSpeedMultiplier = Math.pow(10, attackPower / 100.0); // number between 0 and 10 
            // for power = 60: 4
            // for power = 80: 6.3
            // for power = 100: 10
            if (shootHardSpeedMultiplier < 4) {
                shootHardSpeedMultiplier = 4;
            }

            shootToTeammate = false;
        }
    }

    /**
     * Shoot the ball at the right goal with the speed based on the attack power
     * of the player
     *
     * @param playerID the players ID
     */
    protected static void shootRightGoal(int playerID) {
        if (counter > 1) {
            // decide direction to shoot ball
            double xPos = RIGHT_GOAL_POSITION.getxPos();
            double yPos = RIGHT_GOAL_POSITION.getyPos();
            yPos += (Math.random() * GOAL_SIZE) - (Math.random() * GOAL_SIZE);

            // shoot ball
            shootBallTo(new Position(xPos, yPos), true);
            shootHard = true;

            //assume only players of ally team will shoot the left goal
            int attackPower = CurrentPositions.getAllyInfo(playerID).getAttackPower();
            shootHardSpeedMultiplier = Math.pow(10, attackPower / 100.0); // number between 0 and 10 
            // for power = 60: 4
            // for power = 80: 6.3
            // for power = 100: 10
            if (shootHardSpeedMultiplier < 4) {
                shootHardSpeedMultiplier = 4;
            }

            shootToTeammate = false;
        }
    }

    /**
     * If the ball is moving to the right
     *
     * @return boolean if the ball is moving to the right
     */
    protected static boolean isLastShotByAllyTeam() {
        return lastShotByAllyTeam;
    }

    /**
     * Shoot the ball to a teammate (will make sure the speed is adjusted to
     * match the distance to the teamate)
     *
     * @param to position of the teammate you will shoot to
     * @param ShotByAllyTeam if the ball is shot by the ally team
     */
    protected static void shootToTeammate(Position to, boolean ShotByAllyTeam) {
        if (counter > 1) {
            shootBallTo(to, ShotByAllyTeam);
            shootToTeammate = true;
        }
    }

    /**
     * If the ball is outside of the field
     *
     * @return boolean: if the ball is outside of the field
     */
    public static boolean isOutsideOfField() {
        return isOutsideOfField;
    }

    /**
     * Set if the ball is outside of the field
     *
     * @param isOutsideOfField if the ball should be outside of the field
     */
    public static void setOutsideOfField(boolean isOutsideOfField) {
        BallAI.isOutsideOfField = isOutsideOfField;
    }

    /**
     * Calculate the speed of the ball
     *
     * @return the speed of the ball
     */
    protected static double getBallSpeed() {
        double speed = BALLSPEED;

        if (shootHard) {
            speed *= shootHardSpeedMultiplier;
        }
        if (shootToTeammate && ballVector != null) {
            // give approximation of speed the ball should be shoot at
            double s = ballVector.getLength() + 10.0; //shoot so that ball can go 20 pixels further than the target (if not stopped)
            double v = 5; // velocity
            double d; // distance
            do {
                v += 5;
                d = 0;
                double w = v;
                while (w > 5) {
                    d += w;
                    w /= Math.sqrt(2);
                }
            } while (s - d > 0);
            speed = v;
        }

        if (counter < 15) {
            for (int i = 1; i < counter; i++) {
                speed /= Math.sqrt(2);
            }
        } else {
            return 0;
        }

        if (speed < 5) {
            return 0;
        }
        return speed;
    }

    /**
     * Let the keeper stop the ball
     *
     * @param keeper the position of the keeper
     * @param isOnAllyTeam if the keeper is on the ally team
     */
    protected static void stopBall(Position keeper, boolean isOnAllyTeam) {
        lastShotByAllyTeam = isOnAllyTeam;
        ballStopped = true;
        ballVector = new Vector(currentBallPosition, keeper);
        counter = 50;
    }

    /**
     * Check if the ball is moving toward the left goal
     *
     * @param checkDirection if true, only return true if the ball hasn't passed
     * the goal yet
     * @return if the ball will roll (or has already rolled, if the parameter is
     * false,) into the goal
     */
    protected static boolean isMovingTowardLeftGoal(boolean checkDirection) {
        Position goalUpperBound = LEFT_GOAL_POSITION.getTranslateY(GOAL_SIZE / 2);
        Position goalLowerBound = LEFT_GOAL_POSITION.getTranslateY(-GOAL_SIZE / 2);
        if (checkDirection) {
            return ballVector.intersectsWith(goalUpperBound, goalLowerBound, true, false);
        } else {
            return ballVector.intersectsWith(goalUpperBound, goalLowerBound);
        }
    }

    /**
     * Check if the ball is moving toward the right goal
     *
     * @param checkDirection if true, only return true if the ball hasn't passed
     * the goal yet
     * @return if the ball will roll (or has already rolled, if the parameter is
     * false,) into the goal
     */
    protected static boolean isMovingTowardRightGoal(boolean checkDirection) {
        Position goalUpperBound = RIGHT_GOAL_POSITION.getTranslateY(GOAL_SIZE / 2);
        Position goalLowerBound = RIGHT_GOAL_POSITION.getTranslateY(-GOAL_SIZE / 2);
        if (checkDirection) {
            return ballVector.intersectsWith(goalUpperBound, goalLowerBound, true, true);
        } else {
            return ballVector.intersectsWith(goalUpperBound, goalLowerBound);
        }
    }

    /**
     * Get the position where the ball will intersect with the goal
     *
     * @param isOnAllyTeam if this should check for the ally team goal
     * @return the position where the ball will intersect with the goal
     */
    protected static Position getGoalIntersection(boolean isOnAllyTeam) {
        if (isOnAllyTeam) {
            return ballVector.getIntersectionPoint(LEFT_GOAL_POSITION.getTranslateY(20), LEFT_GOAL_POSITION);
        } else {
            return ballVector.getIntersectionPoint(RIGHT_GOAL_POSITION.getTranslateY(20), RIGHT_GOAL_POSITION);
        }
    }

    /**
     * Check if the ball will be in the goal after the next frame
     *
     * @return if the ball will be in the goal after the next frame
     */
    protected static boolean isNextFrameOutsideField() {
        counter++;
        double speed = getBallSpeed() + 3;
        counter--;

        if (ballVector != null) {
            double nextX = ballVector.translate(currentBallPosition, speed).getxPos();
            return nextX < 60 || nextX > 995;
        } else {
            return false;
        }
    }

}
