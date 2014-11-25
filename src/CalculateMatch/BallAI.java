/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculateMatch;

import ContainerPackage.ExactPosition;



/**
 *
 * @author faris
 */
public class BallAI {
    
    private static ExactPosition currentBallPosition;
    private static ExactPosition finalDesitination;
    private static int counter = 0;
    
    
    public static void shootBallTo(ExactPosition destination){
        finalDesitination = destination;
        counter = 0;
    }
    
    public static ExactPosition getNextBallPosition(){
        counter++;
        double speed = Math.random() * 7.5 + 20.0;
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

    public static ExactPosition getCurrentBallPosition() {
        return currentBallPosition;
    }

    public static void setCurrentBallPosition(ExactPosition BallPosition) {
        currentBallPosition = BallPosition;
    }

    public static ExactPosition getFinalDesitination() {
        return finalDesitination;
    }

}
