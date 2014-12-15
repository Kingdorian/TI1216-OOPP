package CalculateMatch;

import ContainerPackage.ExactPosition;
import ContainerPackage.Vector;

/**
 * This class decides the behaviour of the ball (so it can roll for more than
 * 1 frame and slows down over time)
 * @author faris
 */
public class BallAI {
    
    private static final ExactPosition LEFT_GOAL_POSITION = new ExactPosition(60,381);
    private static final ExactPosition RIGHT_GOAL_POSITION = new ExactPosition(955,381);
    
    private static final int GOAL_SIZE = 80;
    private static final double BALLSPEED = 20.0;
    
    private static ExactPosition currentBallPosition;
    private static Vector ballVector;
    private static int counter = 0;
    private static boolean shootHard;
    private static boolean shootToTeammate;
    private static boolean lastShotByAllyTeam = false;
    
    
    /**
     * set the direction for the ball to move to
     * @param destination   an ExactPosition containing the direction of the ball
     * @param ShotByAllyTeam    true if the ball was shot by a palyer of the ally team
     */
    public static void shootBallTo(ExactPosition destination, boolean ShotByAllyTeam){
        // if ball has not just been shot
        if(counter > 1){
            lastShotByAllyTeam = ShotByAllyTeam;
            shootHard = false;
            shootToTeammate = false;
            ballVector = new Vector(currentBallPosition, destination);
            counter = 0;
        }
    }
    
    
    /**
     * calculate the next location for the ball to move to
     * @return      ExactPosition containing the position the ball will move to next
     */
    public static ExactPosition getNextBallPosition(){
        counter++;
        double speed = Math.random() * 7.5 + BALLSPEED;
        
        if(shootHard)
            speed *= 5;
        if(shootToTeammate && ballVector != null)
            speed = ballVector.getLength()/2;
        
        if(counter < 15){
            for(int i=1; i<counter; i++)
                speed /= Math.sqrt(2);
        } else
            return currentBallPosition;
        if(speed < 5)
            return currentBallPosition;
        
        if(ballVector != null)
            currentBallPosition = ballVector.translate(currentBallPosition, speed);

        return currentBallPosition;
    }

    
    /**
     * gives the current ball position
     * @return      an ExactPosition containing the current ball position
     */
    public static ExactPosition getCurrentBallPosition() {
        return currentBallPosition;
    }
    
    
    /**
     * set the current position of the ball
     * @param BallPosition      an ExactPosition containing the currect location of the ball
     */
    public static void setCurrentBallPosition(ExactPosition BallPosition) {
        currentBallPosition = BallPosition;
    }

    
    public static void shootLeftGoal(){
        if(counter > 1){
            // decide direction to shoot ball
            double xPos = LEFT_GOAL_POSITION.getxPos();
            double yPos = LEFT_GOAL_POSITION.getyPos();
            yPos += (Math.random() *  GOAL_SIZE * 0.8) - (Math.random() *  GOAL_SIZE * 0.8);

            // shoot ball
            shootBallTo(new ExactPosition(xPos, yPos), false);
            shootHard = true;
            shootToTeammate = false;
        }
    }
    
    
    public static void shootRightGoal(){
        if(counter > 1){
            // decide direction to shoot ball
            double xPos = RIGHT_GOAL_POSITION.getxPos();
            double yPos = RIGHT_GOAL_POSITION.getyPos();
            yPos += (Math.random() *  GOAL_SIZE * 0.8) - (Math.random() *  GOAL_SIZE * 0.8);

            // shoot ball
            shootBallTo(new ExactPosition(xPos, yPos), true);
            shootHard = true;
            shootToTeammate = false;
        }
    }
    
    /**
     * return if the ball is moving to the right
     * @return boolean
     */
    public static boolean islastShotByAllyTeam(){
//        if(ballVector == null)
//            return false;
//        return ballVector.getPointFrom().getxPos() < ballVector.getPointTo().getxPos();
        return lastShotByAllyTeam;
    }
    
    
    public static void shootToTeammate(ExactPosition to, boolean ShotByAllyTeam){
        if(counter > 1){
            shootBallTo(to, ShotByAllyTeam);
            shootToTeammate = true;
        }
    }
}
