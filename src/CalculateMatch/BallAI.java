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
    private static final ExactPosition RIGHT_GOAL_POSITION = new ExactPosition(60,955);
    
    private static final int GOAL_SIZE = 80;
    private static final double BALLSPEED = 20.0;
    
    private static ExactPosition currentBallPosition;
   // private static ExactPosition finalDesitination;
    private static Vector ballVector;
    private static int counter = 0;
    private static boolean shootHard;
    
    
    /**
     * set the direction for the ball to move to
     * @param destination   an ExactPosition containing the direction of the ball
     */
    public static void shootBallTo(ExactPosition destination){
        shootHard = false;
        ballVector = new Vector(currentBallPosition, destination);
        //finalDesitination = destination;
        counter = 0;
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
        
        if(counter < 4){
            for(int i=1; i<counter; i++)
                speed /= Math.sqrt(2);
        } else
            return currentBallPosition;

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
        // decide direction to shoot ball
        double xPos = LEFT_GOAL_POSITION.getxPos();
        double yPos = LEFT_GOAL_POSITION.getyPos();
        yPos += (Math.random() *  GOAL_SIZE * 0.8) - (Math.random() *  GOAL_SIZE * 0.8);
        
        // shoot ball
        shootBallTo(new ExactPosition(xPos, yPos));
        shootHard = true;
    }
}
