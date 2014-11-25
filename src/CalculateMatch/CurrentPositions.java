/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculateMatch;

import ContainerPackage.ExactPosition;
import ContainerPackage.PlayerPosition;
import ContainerPackage.Position;
import ContainerPackage.PositionsTimeSlice;
import java.util.ArrayList;

/**
 *
 * @author faris
 */
public class CurrentPositions {
    private ArrayList<ExactPosition> allyTeam = new ArrayList<>();
    private ArrayList<ExactPosition> enemyTeam = new ArrayList<>();
    private ExactPosition ballPosition = new ExactPosition();

    
    /**
     * Constructor: sets start positions
     */
    public CurrentPositions(){
        for(int i=0; i<11; i++){
            allyTeam.add(new ExactPosition());
            enemyTeam.add(new ExactPosition());
        }
        setStartOfMatchPositions();
    }
    
    /**
     * set the start positions
     */
    public final void setStartOfMatchPositions(){
        allyTeam.clear();
        enemyTeam.clear();
        
        // set players of ally team to the right positions
        allyTeam.add(new ExactPosition( 60.0, 381.0));
        allyTeam.add(new ExactPosition(170.0, 250.0));
        allyTeam.add(new ExactPosition(240.0, 312.0));
        allyTeam.add(new ExactPosition(275.0, 385.0));
        allyTeam.add(new ExactPosition(240.0, 462.0));
        allyTeam.add(new ExactPosition(170.0, 512.0));
        allyTeam.add(new ExactPosition(345.0, 290.0));
        allyTeam.add(new ExactPosition(345.0, 492.0));
        allyTeam.add(new ExactPosition(467.0, 282.0));
        allyTeam.add(new ExactPosition(405.0, 382.0));
        allyTeam.add(new ExactPosition(467.0, 485.0));
        
        // set players of enemy team to the right positions
        enemyTeam.add(new ExactPosition(963.0, 381.0));
        enemyTeam.add(new ExactPosition(846.0, 250.0));
        enemyTeam.add(new ExactPosition(785.0, 312.0));
        enemyTeam.add(new ExactPosition(743.0, 385.0));
        enemyTeam.add(new ExactPosition(785.0, 462.0));
        enemyTeam.add(new ExactPosition(846.0, 512.0));
        enemyTeam.add(new ExactPosition(678.0, 290.0));
        enemyTeam.add(new ExactPosition(678.0, 492.0));
        enemyTeam.add(new ExactPosition(520.0, 332.0));
        enemyTeam.add(new ExactPosition(518.0, 390.0));
        enemyTeam.add(new ExactPosition(574.0, 473.0));
        
        // set ball position
        ballPosition = new ExactPosition(510.0, 382.0);
    }
    
    public PositionsTimeSlice convertToTimeSlice(){
        PositionsTimeSlice res = new PositionsTimeSlice();
        for(int i=0; i<11; i++){
            res.setPlayersAlly(new PlayerPosition((int) allyTeam.get(i).getxPos(), (int) allyTeam.get(i).getyPos()), i);
            res.setPlayersAdversary(new PlayerPosition((int) enemyTeam.get(i).getxPos(), (int) enemyTeam.get(i).getyPos()), i);
        }
        res.setBallPosition(new Position((int) ballPosition.getxPos(), (int) ballPosition.getyPos()));
        return res;
    }
    
    /**
     * if player (parameter) is on allyTeam, return all player on enemyTeam to the right of player
     * if player (parameter) is on enemyTeam, return all players on allyTeam to the left of player
     * @param player    the player being checked
     * @return          an arraylist of playerpositions of all opponents in front of player
     */
    public ArrayList<ExactPosition> getEnemiesInFrontOf(ExactPosition player){
        
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
    
    public ExactPosition getClosestEnemyTo(ExactPosition pos){
        ExactPosition res = enemyTeam.get(0);
        for(int i = 1; i<11; i++)
            if(enemyTeam.get(i).distanceTo(pos) < res.distanceTo(pos))
                res = enemyTeam.get(i);
        return res;
    }
    
    public ExactPosition getClosestAllyTo(ExactPosition pos){
        ExactPosition res = allyTeam.get(0);
        for(int i = 1; i<11; i++)
            if(allyTeam.get(i).distanceTo(pos) < res.distanceTo(pos))
                res = allyTeam.get(i);
        return res;
    }
    
    public boolean isAlly(ExactPosition p){
        return allyTeam.contains(p);
    }
    
    public ExactPosition getBallPosition(){
        return ballPosition;
    }
    
    public boolean isClosestToBall(ExactPosition p){
        
        double distance = p.distanceTo(ballPosition);
        
        for(int i=0; i<11; i++){
            if(allyTeam.get(i).distanceTo(ballPosition) < distance || enemyTeam.get(i).distanceTo(ballPosition) < distance)
                return false;
        }
        return true;
    }

    public ArrayList<ExactPosition> getAllyTeam() {
        return allyTeam;
    }

    public ArrayList<ExactPosition> getEnemyTeam() {
        return enemyTeam;
    }

    public void setAllyTeam(ArrayList<ExactPosition> allyTeam) {
        this.allyTeam = allyTeam;
    }

    public void setEnemyTeam(ArrayList<ExactPosition> enemyTeam) {
        this.enemyTeam = enemyTeam;
    }

    public void setBallPosition(ExactPosition ballPosition) {
        this.ballPosition = ballPosition;
    }
    
    
}
