package ContainerPackage;


/**
 * This class stores a positions x and y coordinate as two bytes.
 * Those bytes can be acessed by other classes like it are ints.
 * The x coordinate should have a value between 0 and 1024.
 * The y coordinate should have a value between 0 and 768.
 * The positions aren't stored exactly, but can be 2 pixels off.
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
        if(yPos<767)
            this.yPos =(byte) ((int) ((yPos+1.5)/3) - 128); // turn an int ranging from 0 to 768 to a number which is storeable with a byte
        else
            this.yPos = (byte) 127;
        
        if(xPos<1023)
            this.xPos =(byte) ((int) ((xPos+2)/4) - 128); // turn an int ranging from 0 to 1024 to a number which is storeable with a byte
        else
            this.xPos = (byte) 127;
    }
    
    /**
     * basic constructor without parameters
     */
    public Position(){
    }
    

    /**
     * gives the y position
     * @return      an int containing the y position
     */
    public int getyPos() {
        return (int) ((yPos + 128) * 3); // turn byte value of y position into an integer ranging from 0 to 768
    }

    
    /**
     * gives the x position
     * @return      an int containing the x position
     */
    public int getxPos() {
        return (int) ((xPos + 128) * 4); // turn byte value of x position into an integer ranging from 0 to 1024
    }
}
