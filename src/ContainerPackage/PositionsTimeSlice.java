package ContainerPackage;

/**
 * Container class containing 24 player positions (Player objects), creating the positions for all players of a football game at a point in time.
 * A player object is null if the player isn't on the field for any reason.
 * @author faris
 */
public class PositionsTimeSlice {
    
    // Players are sorted by their numbers, the player with the lowest number will be Player[0], the one after that Player[1], ...
    private PlayerPosition[] playersAlly = new PlayerPosition[12];      // array of players played by the player
    private PlayerPosition[] playersAdversary = new PlayerPosition[12];  // array of players played by the computer
    private Position ballPosition = new Position();
    
    private boolean isPause = false; // if the frame represents a pause, this value will be true

    
    /**
     * basic constructor
     */
    public PositionsTimeSlice(){
    }
    
    
    /**
     * constructor used when this frame should represent a pause.
     * @param isPause   true if this frame is a pause, else false
     */
    public PositionsTimeSlice(boolean isPause){
        this.isPause = isPause;
    }

    
    /**
     * gives a Player object that defines the position of a player
     * @param index     the index of the player (player with the lowest back number will have index 0, the one after that 1, ...)
     * @return          a player object of the player with the indicated index
     */
    public PlayerPosition getPlayersAlly(int index) {
        return playersAlly[index];
    }

    
    /**
     * gives a Player object that defines the position of a player
     * @param index     the index of the Player (Player with the lowest back number will have index 0, the one after that 1, ...)
     * @return          a Player object of the player with the indicated index
     */
    public PlayerPosition getPlayersAdversary(int index) {
        return playersAdversary[index];
    }

    
    /**
     * set a Player of the player (playing the game) in this tis time slice
     * @param playersAlly   a Player object containing the players position
     * @param index         the index of the player (Player with the lowest back number will have index 0, the one after that 1, ...)
     */
    public void setPlayersAlly(PlayerPosition playersAlly, int index ) {
        this.playersAlly[index] = playersAlly;
    }

    
    /**
     * set a Player of the computer in this tis time slice
     * @param playersAdversary  a Player object containing the players position
     * @param index             the index of the player (Player with the lowest back number will have index 0, the one after that 1, ...)
     */
    public void setPlayersAdversary(PlayerPosition playersAdversary, int index) {
        this.playersAdversary[index] = playersAdversary;
    }

    
    /**
     * gives the balls position
     * @return      the Position of the ball
     */
    public Position getBallPosition() {
        return ballPosition;
    }

    
    /**
     * set the position of the ball
     * @param ballPosition      a Position containing the balls position
     */
    public void setBallPosition(Position ballPosition) {
        this.ballPosition = ballPosition;
    }
    
}
