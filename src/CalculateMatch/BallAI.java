package CalculateMatch;

import ContainerPackage.ExactPosition;

/**
 * This class decides the behaviour of the ball (so it can roll for more than
 * 1 frame and slows down over time)
 * @author faris
 */
public class BallAI {
    final static double BALLSPEED = 20.0;
    
    private static ExactPosition currentBallPosition;
    private static ExactPosition finalDesitination;
    private static int counter = 0;
    
    /**
     * set the direction for the ball to move to
     * @param destination   an ExactPosition containing the direction of the ball
     */
    public static void shootBallTo(ExactPosition destination){
        finalDesitination = destination;
        counter = 0;
    }
    
    
    /**
     * calculate the next location for the ball to move to
     * @return      ExactPosition containing the position the ball will move to next
     */
    public static ExactPosition getNextBallPosition(){
        counter++;
        double speed = Math.random() * 7.5 + BALLSPEED;
        if(counter < 4){
            for(int i=1; i<counter; i++)
                speed /= Math.sqrt(2);
        } else
            return currentBallPosition;
        
        if(currentBallPosition.distanceTo(finalDesitination) != 0){
            double factor = speed/currentBallPosition.distanceTo(finalDesitination);
            double xBall = (finalDesitination.getxPos() - currentBallPosition.getxPos()) * factor + currentBallPosition.getxPos();
            double yBall = (finalDesitination.getyPos() - currentBallPosition.getyPos()) * factor + currentBallPosition.getyPos();

            currentBallPosition = new ExactPosition((int) xBall, (int) yBall);

            //return new Position((int) xBall, (int) yBall);
        } //else
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

    
    /**
     * get the direction which the ball is kicked to
     * @return      an ExactPosition containing the direction the ball is kicked to
     */
    public static ExactPosition getFinalDesitination() {
        return finalDesitination;
    }
}
