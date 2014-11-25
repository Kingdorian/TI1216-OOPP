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
public class Position {
    private byte xPos; // stores x position
    private byte yPos; // stores y position
    
    /**
     * constructor with player positions
     * @param xPos
     * @param yPos 
     */
    public Position(int xPos, int yPos){
        this.yPos =(byte) ((int) (yPos/3) - 128); // turn an int ranging from 0 to 1024 to a number which is storeable with a byte
        this.xPos =(byte) ((int) (xPos/4) - 128); // turn an int ranging from 0 to 768 to a number which is storeable with a byte
    }
    
    /**
     * basic constructor without parameters
     */
    public Position(){
    }
    

    public int getyPos() {
        return (int) ((yPos + 128) * 3); // turn byte value of y position into an integer ranging from 0 to 768
    }

    public int getxPos() {
        return (int) ((xPos + 128) * 4); // turn byte value of x position into an integer ranging from 0 to 1024
    }

    public void setyPos(int yPos) {
        this.yPos =(byte) ((int) (yPos/3) - 128); // turn an int ranging from 0 to 768 to a number which is storeable with a byte
    }

    public void setxPos(int xPos) {
        this.xPos = (byte) ((int) (xPos/4) - 128); // turn an int ranging from 0 to 1024 to a number which is storeable with a byte
    }
    
    /**
     * gives the distance between this player and other player
     * @param other  player to get disntance to
     * @return       double: the distance
     */
    public double distanceTo(Position other){
        if(other == null)
            return 100000;
        return Math.sqrt(Math.pow(this.getxPos() - other.getxPos(), 2) + Math.pow(this.getyPos() - other.getyPos(), 2)) ;
    }
    
    
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Position))
            return false;
        
        Position other = (Position) o;
        return other.getxPos() == this.getxPos() && other.getyPos() == this.getyPos();
    }
}
