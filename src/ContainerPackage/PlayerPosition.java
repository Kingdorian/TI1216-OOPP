package ContainerPackage;

/**
 * This is a container class.
 * It saves the position of a player with 2 bytes.
 * Users can treat this class as if the data is stored as an integer.
 * This class is used to store positions on a football field with a max width of 1024 units of measurement and a max height of 768 units of measurement (typically pixels).
 * @author faris
 */
public final class PlayerPosition extends Position {
    
    private boolean onField = true; // stores if the player is currently on the field
    
    
    /**
     * constructor with player positions
     * @param xPos
     * @param yPos 
     */
    public PlayerPosition(int xPos, int yPos){
        super(xPos, yPos);
    }
    
    
    /**
     * basic constructor without parameters
     */
    public PlayerPosition(){
        super();
    }
    
    
    /**
     * gives if this player is on the field
     * @return      a boolean: is the player currently on the field?
     */
    public boolean isOnField() {
        return onField;
    }

    
    /**
     * set: is this player on the field?
     * @param inField       a boolean: is the player currently on the field?
     */
    public void setOnField(boolean inField) {
        this.onField = inField;
    }
}
