package application.animation.Container;

import application.animation.CalculateMatch.BallAI;
import java.util.ArrayList;

/**
 * Store the current positions of the players with additional information about
 * the players, so the AI classes can use it. This class also contains a lot of
 * usefull methods which can be executed on the current positions of the
 * players.
 *
 * @author Faris
 */
public class CurrentPositions {

    private ArrayList<Position> allyTeam = new ArrayList<>();
    private ArrayList<Position> enemyTeam = new ArrayList<>();

    // Specific player information
    private final static ArrayList<PlayerInfo> allyInfo = new ArrayList<>();
    private final static ArrayList<PlayerInfo> enemyInfo = new ArrayList<>();

    private static int scoreLeft;
    private static int scoreRight;

    /**
     * Constructor: sets start positions
     */
    public CurrentPositions() {
        for (int i = 0; i < 11; i++) {
            allyTeam.add(new Position());
            enemyTeam.add(new Position());
        }
    }

    /**
     * Sets the players to the start of match positions.
     */
    public final void setStartOfMatchPositions() {
        // clear lists
        allyTeam.clear();
        enemyTeam.clear();

        // set players of ally team to the right positions
        allyTeam.add(new Position(60.0, 381.0));
        allyTeam.add(new Position(170.0, 250.0));
        allyTeam.add(new Position(240.0, 312.0));
        allyTeam.add(new Position(240.0, 462.0));
        allyTeam.add(new Position(170.0, 512.0));
        allyTeam.add(new Position(275.0, 385.0));
        allyTeam.add(new Position(345.0, 290.0));
        allyTeam.add(new Position(345.0, 492.0));
        allyTeam.add(new Position(467.0, 282.0));
        allyTeam.add(new Position(405.0, 382.0));
        allyTeam.add(new Position(467.0, 485.0));

        // set players of enemy team to the right positions
        enemyTeam.add(new Position(963.0, 381.0));
        enemyTeam.add(new Position(846.0, 250.0));
        enemyTeam.add(new Position(785.0, 312.0));
        enemyTeam.add(new Position(785.0, 462.0));
        enemyTeam.add(new Position(846.0, 512.0));
        enemyTeam.add(new Position(743.0, 385.0));
        enemyTeam.add(new Position(678.0, 290.0));
        enemyTeam.add(new Position(678.0, 492.0));
        enemyTeam.add(new Position(520.0, 332.0));
        enemyTeam.add(new Position(518.0, 390.0));
        enemyTeam.add(new Position(574.0, 473.0));

        // set ball position
        BallAI.setCurrentBallPosition(new Position(510.0, 382.0));
        BallAI.setOutsideOfField(false);
    }

    /**
     * Convert the positions saved in this class to a positions frame.
     *
     * @return a frame containing the current positions
     */
    public PositionFrame convertToFrame() {

        // if the ball is outside of the field, return a pause slice, else return next positions.
        if (BallAI.isOutsideOfField()) {
            PositionFrame res = new PositionFrame();
            res.setPause(true);
            res.setScoreLeft(scoreLeft);
            res.setScoreRight(scoreRight);
            setStartOfMatchPositions();
            return res;
        } else {
            PositionFrame res = new PositionFrame();
            for (int i = 0; i < 11; i++) {
                res.setPlayerAlly(allyTeam.get(i), i);
                res.setPlayerAdversary(enemyTeam.get(i), i);
            }
            res.setBallPosition(BallAI.getCurrentBallPosition());
            res.setScoreLeft(scoreLeft);
            res.setScoreRight(scoreRight);
            return res;
        }
    }

    /**
     * If player (parameter) is on allyTeam, return all player on enemyTeam to
     * the right of player. If player (parameter) is on enemyTeam, return all
     * players on allyTeam to the left of player
     *
     * @param player the player being checked
     * @param isOnAllyTeam if the player is on the ally team
     * @return an arraylist of playerpositions of all opponents in front of
     * player
     */
    public ArrayList<Position> getOpponentsInFrontOf(Position player, boolean isOnAllyTeam) {

        ArrayList<Position> res = new ArrayList<>();

        if (isOnAllyTeam) {
            for (Position pp : enemyTeam) {
                if (player.getxPos() + 16 < pp.getxPos()) {
                    res.add(pp);
                }
            }
        } else {
            for (Position pp : allyTeam) {
                if (player.getxPos() - 16 > pp.getxPos()) {
                    res.add(pp);
                }
            }
        }

        return res;
    }

    /**
     * Gives the position of the closest player from enemyTeam to the given
     * position
     *
     * @param pos the position to compare to
     * @return position of the closest enemyTeam player
     */
    public Position getClosestEnemyTo(Position pos) {
        Position res = enemyTeam.get(0);
        for (int i = 1; i < 11; i++) {
            if (enemyTeam.get(i).distanceTo(pos) < res.distanceTo(pos)) {
                res = enemyTeam.get(i);
            }
        }
        return res;
    }

    /**
     * Gets the closest enemy in front of the player
     *
     * @param pos the position to compare to
     * @return position position of the closest enemy in fron of pos
     */
    public Position getClosestEnemyInFrontOf(Position pos) {
        Position res = enemyTeam.get(0);
        for (int i = 1; i < 11; i++) {
            if (enemyTeam.get(i).getxPos() + 10 > pos.getxPos() && enemyTeam.get(i).distanceTo(pos) < res.distanceTo(pos)) {
                res = enemyTeam.get(i);
            }
        }
        return res;
    }

    /**
     * gives the position of the closest player from allyTeam to the given
     * position
     *
     * @param pos the position to compare to
     * @return position of the closest allyTeam player
     */
    public Position getClosestAllyTo(Position pos) {
        Position res = allyTeam.get(0);
        for (int i = 1; i < 11; i++) {
            if (allyTeam.get(i).distanceTo(pos) < res.distanceTo(pos)) {
                res = allyTeam.get(i);
            }
        }
        return res;
    }

    /**
     * Gets the closest ally in front of the player
     *
     * @param pos the position to compare to
     * @return position of the closest ally in fron of pos
     */
    public Position getClosestAllyInFrontOf(Position pos) {
        Position res = allyTeam.get(0);
        for (int i = 1; i < 11; i++) {
            if (enemyTeam.get(i).getxPos() - 10 < pos.getxPos() && allyTeam.get(i).distanceTo(pos) < res.distanceTo(pos)) {
                res = allyTeam.get(i);
            }
        }
        return res;
    }

    /**
     * True if the player is the closest player to the ball
     *
     * @param p player position
     * @return true if closest player to ball
     */
    public boolean isClosestToBall(Position p) {

        double distance = p.distanceTo(BallAI.getCurrentBallPosition());

        for (int i = 0; i < 11; i++) {
            if (allyTeam.get(i).distanceTo(BallAI.getCurrentBallPosition()) < distance || enemyTeam.get(i).distanceTo(BallAI.getCurrentBallPosition()) < distance) {
                return false;
            }
        }
        return true;
    }

    /**
     * gives the positions of the players from the allyTeam
     *
     * @return an arrayList containing the positions of the allyTeam
     */
    public ArrayList<Position> getAllyTeam() {
        return allyTeam;
    }

    /**
     * gives the positions of the players from the enemyTeam
     *
     * @return an arrayList containing the positions of the enemyTeam
     */
    public ArrayList<Position> getEnemyTeam() {
        return enemyTeam;
    }

    /**
     * Get the ally team info
     *
     * @param id ID of the player
     * @return player info
     */
    public static PlayerInfo getAllyInfo(int id) {
        return allyInfo.get(id);
    }

    /**
     * Set the ally team info
     *
     * @param allyInf PlayerInfo
     * @param id the id of the player
     */
    public static void setAllyInfo(PlayerInfo allyInf, int id) {
        allyInfo.add(id, allyInf);
    }

    /**
     * Set the enemy team info
     *
     * @param id
     * @return player info
     */
    public static PlayerInfo getEnemyInfo(int id) {
        return enemyInfo.get(id);
    }

    /**
     * Set the enemy team info
     *
     * @param enemyInf PlayerInfo
     * @param id ID of the player
     */
    public static void setEnemyInfo(PlayerInfo enemyInf, int id) {
        enemyInfo.add(id, enemyInf);
    }

    /**
     * Get the ID of the player by using it's position Use this only when
     * absolutely necesarry, can give wrong id if 2 players are standing on
     * exactly the same position.
     *
     * @param player players position
     * @return players ID
     */
    public int getIDByPosition(Position player) {
        int i;
        for (i = 0; i < 11; i++) {
            if (player.equals(allyTeam.get(i)) || player.equals(enemyTeam.get(i))) {
                break;
            }
        }
        return i;
    }

    /**
     * Adds a point to the score of the left team
     */
    public static void addPointLeft() {
        scoreLeft++;
    }

    /**
     * Adds a point to the score of the right team
     */
    public static void addPointRight() {
        scoreRight++;
    }

    /**
     * Get the score of the left team
     *
     * @return the score of the left team
     */
    public static int getScoreLeft() {
        return scoreLeft;
    }

    /**
     * Get the score of the right team
     *
     * @return the score of the right team
     */
    public static int getScoreRight() {
        return scoreRight;
    }

    /**
     * Reset the scores
     */
    public static void reset() {
        scoreLeft = 0;
        scoreRight = 0;
    }

}
