package application.animation.Container;

/**
 * Container class containing 22 player positions, defining the positions for
 * all players of a football game. A player object is null if the player isn't
 * on the field for any reason.
 *
 * @author Faris
 */
public class PositionFrame {

    // Players are sorted by their numbers, the player with the lowest number will be Player[0], the one after that Player[1], ...
    private final Position[] playersAlly = new Position[11];      // array of players played by the player
    private final Position[] playersAdversary = new Position[11];  // array of players played by the computer
    private Position ballPosition = new Position();

    private boolean isPause = false; // if the frame represents a pause, this value will be true
    private int scoreRight = 0;
    private int scoreLeft = 0;

    /**
     * basic constructor
     */
    public PositionFrame() {
    }

    /**
     * gives a Player object that defines the position of a player
     *
     * @param id the index of the player (player with the lowest back number
     * will have index 0, the one after that 1, ...)
     * @return a player object of the player with the indicated index
     */
    public Position getPlayerAlly(int id) {
        return playersAlly[id];
    }

    /**
     * gives a Player object that defines the position of a player
     *
     * @param id the index of the Player (Player with the lowest back number
     * will have index 0, the one after that 1, ...)
     * @return a Player object of the player with the indicated index
     */
    public Position getPlayerAdversary(int id) {
        return playersAdversary[id];
    }

    /**
     * set a Player of the player (playing the game) in this tis time slice
     *
     * @param player a Player object containing the players position
     * @param id the index of the player (Player with the lowest back number
     * will have index 0, the one after that 1, ...)
     */
    public void setPlayerAlly(Position player, int id) {
        this.playersAlly[id] = player;
    }

    /**
     * set a Player of the computer in this tis time slice
     *
     * @param player a Player object containing the players position
     * @param id the index of the player (Player with the lowest back number
     * will have index 0, the one after that 1, ...)
     */
    public void setPlayerAdversary(Position player, int id) {
        this.playersAdversary[id] = player;
    }

    /**
     * gives the balls position
     *
     * @return the Position of the ball
     */
    public Position getBallPosition() {
        return ballPosition;
    }

    /**
     * Set the position of the ball
     *
     * @param ballPosition a Position containing the balls position
     */
    public void setBallPosition(Position ballPosition) {
        this.ballPosition = ballPosition;
    }

    /**
     * Returns true if this is a pause frame (ex. after a goal)
     *
     * @return boolean: if this is a pause frame
     */
    public boolean isPause() {
        return isPause;
    }

    /**
     * Set if this frame should be a pause frame.
     *
     * @param isPause boolean: if this should be set to a pause frame
     */
    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    /**
     * Get the amount of points made by the right team
     *
     * @return amount of points made by the right team
     */
    public int getScoreRight() {
        return scoreRight;
    }

    /**
     * Set the amount of points made by the right team
     *
     * @param scoreRight amount of points made by the right team
     */
    public void setScoreRight(int scoreRight) {
        this.scoreRight = scoreRight;
    }

    /**
     * Get the amount of points made by the left team
     *
     * @return amount of points made by the left team
     */
    public int getScoreLeft() {
        return scoreLeft;
    }

    /**
     * Set the amount of points made by the left team
     *
     * @param scoreLeft amount of points made by the left team
     */
    public void setScoreLeft(int scoreLeft) {
        this.scoreLeft = scoreLeft;
    }

}
