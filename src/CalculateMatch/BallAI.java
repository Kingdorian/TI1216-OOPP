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
    private static int counter = 2;
    private static boolean shootHard;
    private static boolean shootToTeammate;
    private static boolean lastShotByAllyTeam = false;
    private static boolean isOutsideOfField = false;
    
    
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
        
        //if ball is outside of field, return ball position and set outside of field variable to true
        isOutsideOfField = false;
        if(checkBallOutsideOfField()){
            isOutsideOfField = true;
            return currentBallPosition;
        }
        
        counter++;
        double speed = Math.random() * 7.5 + BALLSPEED;
        
        if(shootHard)
            speed *= 5;
        if(shootToTeammate && ballVector != null)
            speed = ballVector.getLength()/3;
        
        if(counter < 15){
            for(int i=1; i<counter; i++)
                speed /= Math.sqrt(2);
        } else
            return currentBallPosition;
        if(speed < 5)
            return currentBallPosition;
        
        if(ballVector != null)
            currentBallPosition = ballVector.translate(currentBallPosition, speed);

        // TODO: Check for ball outside of field <><><><><><><><><><><><><><><><><><><><><><><><>
        
        return currentBallPosition;
    }
    
    /**
     * Checks if the ball is currently outside of the field
     * @return 
     */
    private static boolean checkBallOutsideOfField(){
        double xPos = currentBallPosition.getxPos();
        double yPos = currentBallPosition.getyPos();
        
        // check if the ball is outside the field
        if(xPos < 60)
            if(yPos < LEFT_GOAL_POSITION.getyPos() + (GOAL_SIZE/2) && yPos > LEFT_GOAL_POSITION.getyPos() - (GOAL_SIZE/2)){
                CurrentPositions.addPointRight(); // left team scored
                return true;
            } else 
                return true;
        else if(xPos > 995)
            if(yPos < RIGHT_GOAL_POSITION.getyPos() + (GOAL_SIZE/2) && yPos > RIGHT_GOAL_POSITION.getyPos() - (GOAL_SIZE/2)){
                CurrentPositions.addPointLeft(); // right team scored
                return true;
            } else 
                return true;
        else
            return yPos < 58 || yPos > 703;
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
            yPos += (Math.random() *  GOAL_SIZE) - (Math.random() *  GOAL_SIZE);

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
            yPos += (Math.random() *  GOAL_SIZE) - (Math.random() *  GOAL_SIZE);

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
        return lastShotByAllyTeam;
    }
    
    
    public static void shootToTeammate(ExactPosition to, boolean ShotByAllyTeam){
        if(counter > 1){
            shootBallTo(to, ShotByAllyTeam);
            shootToTeammate = true;
        }
    }

    public static boolean isOutsideOfField() {
        return isOutsideOfField;
    }

    public static void setOutsideOfField(boolean isOutsideOfField) {
        BallAI.isOutsideOfField = isOutsideOfField;
    }

}
