/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContainerPackage;

/**
 *
 * @author faris
 */
public class ExactPosition {
    private double xPos; // stores x position
    private double yPos; // stores y position
    
    /**
     * constructor with player positions
     * @param xPos
     * @param yPos 
     */
    public ExactPosition(double xPos, double yPos){
        this.yPos = yPos;
        this.xPos = xPos;
        
        // make sure nothing will ever get drawn outside the screen
        if(yPos < 0)
            this.yPos = 0;
        if(xPos < 0)
            this.xPos = 0;
        if(yPos > 768)
            this.yPos = 768;
        if(xPos > 1024)
            this.xPos = 1024;
    }
       
    /**
     * basic constructor without parameters
     */
    public ExactPosition(){
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;

        if(xPos < 0)
            this.xPos = 0;
        if(xPos > 1024)
            this.xPos = 1024;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
        
        if(yPos < 0)
            this.xPos = 0;
        if(yPos > 768)
            this.xPos = 768;
    }
    
    
    /**
     * gives the distance between this player and other player
     * @param other  player to get disntance to
     * @return       double: the distance
     */
    public double distanceTo(ExactPosition other){
        if(other == null)
            return 100000;
        return Math.sqrt(Math.pow(this.getxPos() - other.getxPos(), 2) + Math.pow(this.getyPos() - other.getyPos(), 2)) ;
    }
    
    
    @Override
    public boolean equals(Object o){
        if(!(o instanceof ExactPosition))
            return false;
        
        ExactPosition other = (ExactPosition) o;
        return other.getxPos() == this.getxPos() && other.getyPos() == this.getyPos();
    }
}
