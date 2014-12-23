package CalculateMatch;

import ContainerPackage.ExactPosition;
import ContainerPackage.PlayerInfo;
import ContainerPackage.PositionsTimeSlice;
import java.util.ArrayList;

/**
 * Store the positions of the players way more precise than a PositionsTimeSlice,
 * so it's easier to do calculations with them, but it also takes more space, so
 * for storing multiple sets of positions you should use a PositionsTimeSlice.
 * @author faris
 */
public class CurrentPositions {
    
    private ArrayList<ExactPosition> allyTeam = new ArrayList<>();
    private ArrayList<ExactPosition> enemyTeam = new ArrayList<>();
    
    // Specific player information
    private static ArrayList<PlayerInfo> allyInfo = new ArrayList<>();
    private static ArrayList<PlayerInfo> enemyInfo = new ArrayList<>();
    
    private static int scoreLeft;
    private static int scoreRight;
    
    /**
     * Constructor: sets start positions
     */
    public CurrentPositions(){
        for(int i=0; i<11; i++){
            allyTeam.add(new ExactPosition());
            enemyTeam.add(new ExactPosition());
        }
    }
    
    
    /**
     * set the start positions
     */
    public final void setStartOfMatchPositions(){
        // clear lists
        allyTeam.clear();
        enemyTeam.clear();
        
        // set players of ally team to the right positions
        allyTeam.add(new ExactPosition( 60.0, 381.0));
        allyTeam.add(new ExactPosition(170.0, 250.0));
        allyTeam.add(new ExactPosition(240.0, 312.0));
        allyTeam.add(new ExactPosition(240.0, 462.0));
        allyTeam.add(new ExactPosition(170.0, 512.0));
        allyTeam.add(new ExactPosition(275.0, 385.0));
        allyTeam.add(new ExactPosition(345.0, 290.0));
        allyTeam.add(new ExactPosition(345.0, 492.0));
        allyTeam.add(new ExactPosition(467.0, 282.0));
        allyTeam.add(new ExactPosition(405.0, 382.0));
        allyTeam.add(new ExactPosition(467.0, 485.0));
        
        // set players of enemy team to the right positions
        enemyTeam.add(new ExactPosition(963.0, 381.0));
        enemyTeam.add(new ExactPosition(846.0, 250.0));
        enemyTeam.add(new ExactPosition(785.0, 312.0));
        enemyTeam.add(new ExactPosition(785.0, 462.0));
        enemyTeam.add(new ExactPosition(846.0, 512.0));
        enemyTeam.add(new ExactPosition(743.0, 385.0));
        enemyTeam.add(new ExactPosition(678.0, 290.0));
        enemyTeam.add(new ExactPosition(678.0, 492.0));
        enemyTeam.add(new ExactPosition(520.0, 332.0));
        enemyTeam.add(new ExactPosition(518.0, 390.0));
        enemyTeam.add(new ExactPosition(574.0, 473.0));
        
        // set ball position
        BallAI.setCurrentBallPosition(new ExactPosition(510.0, 382.0));
        BallAI.setOutsideOfField(false);
    }
    
    
    /**
     * Convert the positions saved in this class to a positions time slice.
     * A position time slice stores the positions way more efficient, but less precise.
     * @return 
     */
    public PositionsTimeSlice convertToTimeSlice(){
        
        // if the ball is outside of the field, return a pause slice, else return next positions.
        if(BallAI.isOutsideOfField()){
            PositionsTimeSlice res = new PositionsTimeSlice();
            res.setPause(true);
            res.setScoreLeft(scoreLeft);
            res.setScoreRight(scoreRight);
            setStartOfMatchPositions();
            return res;
        } else{
            PositionsTimeSlice res = new PositionsTimeSlice();
            for(int i=0; i<11; i++){
                res.setPlayersAlly(allyTeam.get(i), i);
                res.setPlayersAdversary(enemyTeam.get(i), i);
            }
            res.setBallPosition(BallAI.getCurrentBallPosition());
            res.setScoreLeft(scoreLeft);
            res.setScoreRight(scoreRight);
            return res;
        }
    }
    
    
    /**
     * if player (parameter) is on allyTeam, return all player on enemyTeam to the right of player
     * if player (parameter) is on enemyTeam, return all players on allyTeam to the left of player
     * @param player    the player being checked
     * @return          an arraylist of playerpositions of all opponents in front of player
     */
    public ArrayList<ExactPosition> getOpponentsInFrontOf(ExactPosition player){
        
        ArrayList<ExactPosition> res = new ArrayList<>();
        
        if(allyTeam.contains(player)){
            for(ExactPosition pp : enemyTeam)
                if(player.getxPos() + 16 < pp.getxPos())
                    res.add(pp);
        } else
            for(ExactPosition pp : allyTeam)
                if(player.getxPos() - 16 > pp.getxPos())
                    res.add(pp);
        
        return res;
    }
    
    
    /**
     * gives the position of the closest player from enemyTeam to the given position
     * @param pos   the position to compare to
     * @return      position of the closest enemyTeam player
     */
    public ExactPosition getClosestEnemyTo(ExactPosition pos){
        ExactPosition res = enemyTeam.get(0);
        for(int i = 1; i<11; i++)
            if(enemyTeam.get(i).distanceTo(pos) < res.distanceTo(pos))
                res = enemyTeam.get(i);
        return res;
    }
    
    
    public ExactPosition getClosestEnemyInFrontOf(ExactPosition pos){
        ExactPosition res = enemyTeam.get(0);
        for(int i = 1; i<11; i++)
            if(enemyTeam.get(i).getxPos() + 10 > pos.getxPos() && enemyTeam.get(i).distanceTo(pos) < res.distanceTo(pos))
                res = enemyTeam.get(i);
        return res;
    }
    
    
    /**
     * gives the position of the closest player from allyTeam to the given position
     * @param pos   the position to compare to
     * @return      position of the closest allyTeam player
     */
    public ExactPosition getClosestAllyTo(ExactPosition pos){
        ExactPosition res = allyTeam.get(0);
        for(int i = 1; i<11; i++)
            if(allyTeam.get(i).distanceTo(pos) < res.distanceTo(pos))
                res = allyTeam.get(i);
        return res;
    }
    
    public ExactPosition getClosestAllyInFrontOf(ExactPosition pos){
        ExactPosition res = allyTeam.get(0);
        for(int i = 1; i<11; i++)
            if(enemyTeam.get(i).getxPos() - 10 < pos.getxPos() && allyTeam.get(i).distanceTo(pos) < res.distanceTo(pos))
                res = allyTeam.get(i);
        return res;
    }
    
    
    public boolean isClosestToBall(ExactPosition p){
        
        double distance = p.distanceTo(BallAI.getCurrentBallPosition());
        
        for(int i=0; i<11; i++){
            if(allyTeam.get(i).distanceTo(BallAI.getCurrentBallPosition()) < distance || enemyTeam.get(i).distanceTo(BallAI.getCurrentBallPosition()) < distance)
                return false;
        }
        return true;
    }
    
    
    /**
     * gives the positions of the players from the allyTeam
     * @return      an arrayList containing the positions of the allyTeam
     */
    public ArrayList<ExactPosition> getAllyTeam() {
        return allyTeam;
    }

    
    /**
     * gives the positions of the players from the enemyTeam
     * @return      an arrayList containing the positions of the enemyTeam
     */
    public ArrayList<ExactPosition> getEnemyTeam() {
        return enemyTeam;
    }    

    public static PlayerInfo getAllyInfo(int i) {
        return allyInfo.get(i);
    }

    public static void setAllyInfo(PlayerInfo allyInf, int i) {
        allyInfo.add(i, allyInf);
    }

    public static PlayerInfo getEnemyInfo(int i) {
        return enemyInfo.get(i);
    }

    public static void setEnemyInfo(PlayerInfo enemyInf, int i) {
        enemyInfo.add(i, enemyInf);
    }
    
    // use this only when absolutely necesarry, can give wrong id if 2 players are standing on exactly the same position.
    public int getIDByPosition(ExactPosition player){
        int i;
        for(i=0; i<11; i++)
            if(player.equals(allyTeam.get(i)) || player.equals(enemyTeam.get(i)))
                break;
        return i;
    }
    
    public static void addPointLeft(){
        scoreLeft++;
    }
    
    public static void addPointRight(){
        scoreRight++;
    }

    public static int getScoreLeft() {
        return scoreLeft;
    }

    public static int getScoreRight() {
        return scoreRight;
    }
    
    
}
