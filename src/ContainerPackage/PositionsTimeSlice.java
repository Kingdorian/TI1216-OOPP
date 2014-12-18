package ContainerPackage;

/**
 * Container class containing 24 player positions (Player objects), creating the positions for all players of a football game at a point in time.
 * A player object is null if the player isn't on the field for any reason.
 * @author faris
 */
public class PositionsTimeSlice {
    
    // Players are sorted by their numbers, the player with the lowest number will be Player[0], the one after that Player[1], ...
    private ExactPosition[] playersAlly = new ExactPosition[12];      // array of players played by the player
    private ExactPosition[] playersAdversary = new ExactPosition[12];  // array of players played by the computer
    private ExactPosition ballPosition = new ExactPosition();
    
    private boolean isPause = false; // if the frame represents a pause, this value will be true
    private int scoreRight = 0;
    private int scoreLeft = 0;

    
    /**
     * basic constructor
     */
    public PositionsTimeSlice(){
    }

    
    /**
     * gives a Player object that defines the position of a player
     * @param index     the index of the player (player with the lowest back number will have index 0, the one after that 1, ...)
     * @return          a player object of the player with the indicated index
     */
    public ExactPosition getPlayersAlly(int index) {
        return playersAlly[index];
    }

    
    /**
     * gives a Player object that defines the position of a player
     * @param index     the index of the Player (Player with the lowest back number will have index 0, the one after that 1, ...)
     * @return          a Player object of the player with the indicated index
     */
    public ExactPosition getPlayersAdversary(int index) {
        return playersAdversary[index];
    }

    
    /**
     * set a Player of the player (playing the game) in this tis time slice
     * @param playersAlly   a Player object containing the players position
     * @param index         the index of the player (Player with the lowest back number will have index 0, the one after that 1, ...)
     */
    public void setPlayersAlly(ExactPosition playersAlly, int index ) {
        this.playersAlly[index] = playersAlly;
    }

    
    /**
     * set a Player of the computer in this tis time slice
     * @param playersAdversary  a Player object containing the players position
     * @param index             the index of the player (Player with the lowest back number will have index 0, the one after that 1, ...)
     */
    public void setPlayersAdversary(ExactPosition playersAdversary, int index) {
        this.playersAdversary[index] = playersAdversary;
    }

    
    /**
     * gives the balls position
     * @return      the Position of the ball
     */
    public ExactPosition getBallPosition() {
        return ballPosition;
    }

    
    /**
     * set the position of the ball
     * @param ballPosition      a Position containing the balls position
     */
    public void setBallPosition(ExactPosition ballPosition) {
        this.ballPosition = ballPosition;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    public int getScoreRight() {
        return scoreRight;
    }

    public void setScoreRight(int scoreRight) {
        this.scoreRight = scoreRight;
    }

    public int getScoreLeft() {
        return scoreLeft;
    }

    public void setScoreLeft(int scoreLeft) {
        this.scoreLeft = scoreLeft;
    }
    
    
}
