/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.ContainerPackage;

import application.model.Goalkeeper;
import application.model.Player;
import application.model.Players;
import application.model.Team;
import java.util.ArrayList;
import javafx.scene.shape.Circle;

/**
 *
 * @author Faris
 */
public class TeamPositions {
    
    Team team;
    ArrayList<Players> players;
    
    ArrayList<PlayerIndex> selectedPlayers = new ArrayList<>();

    /**
     * Class to store a combination of playerinfo and index
     */
    private class PlayerIndex {
        public Players player;
        public int index;
        public Circle circle;
        
        /**
         * Constructor
         * @param player the player
         * @param index the index
         */
        PlayerIndex(Players player, Circle circle, int index){
            this.player = player;
            this.circle = circle;
            this.index = index;
        }
        
        /**
         * equals method
         * @param o object to compare to
         * @return if this and o are equal
         */
        @Override
        public boolean equals(Object o){
            if(!(o instanceof PlayerIndex))
                return false;
            PlayerIndex other = (PlayerIndex) o;
            return this.player == other.player || this.index == other.index; // either index or player has to be the same
        }
    }
    
    /**
     * Constructor
     * @param team team of which this are the teams positions
     */
    public TeamPositions(Team team){
        this.team = team;
        players = team.getPlayers();
    }
    
    public void addPlayer(Players player, Circle circle, int id){
        remove(player, circle, id);
        selectedPlayers.add(new PlayerIndex(player, circle, id));
    }

    /**
     * remove each player with the same index OR player info from the player lists
     * @param player 
     */
    private void remove(Players player, Circle circle, int index){

        for (int i = 0; i < selectedPlayers.size(); i++) {
            PlayerIndex pi = selectedPlayers.get(i);
            if(pi.player.equals(player) || pi.circle.equals(circle) || pi.index == index) 
                selectedPlayers.remove(i--); // lower i, so no instance will be skipped
        }
    }
    
    /**
     * get a player with a certain index
     * @param index the index of the player
     * @return the player (or null, if there is no player with the specified index)
     */
    public Players getPlayer(int index){
        
        for (PlayerIndex pi : selectedPlayers) {
            if(pi.index == index)
                return pi.player;
        }
            
        return null;
    }
    
    public ArrayList<Players> getPlayers(){
        ArrayList<Players> result = new ArrayList<>();
        
        for (PlayerIndex pi : selectedPlayers) 
            result.add(pi.player);
        
        return result;
    }
    
    public PlayerInfo getKeeper(){
        
        Goalkeeper keeper = null;
        Circle circle = null;
        
        for (PlayerIndex selectedPlayer : selectedPlayers) {
            if(selectedPlayer.player instanceof Goalkeeper){
                keeper = (Goalkeeper) selectedPlayer.player;
                circle = selectedPlayer.circle;
            }
        }
        if(keeper == null || circle == null)
            return null;
        
        return new PlayerInfo(keeper.getStopPower(), keeper.getPenaltyStopPower(), 
                new ExactPosition(circle.getCenterX(), circle.getCenterY()));
    }
    
    public ArrayList<PlayerInfo> getDefenders(){
        
        ArrayList<PlayerInfo> result = new ArrayList<>();
        
        Player defender;
        Circle circle;
        
        for (PlayerIndex selectedPlayer : selectedPlayers) {
            if(selectedPlayer.circle.getCenterX() < 358 && selectedPlayer.player instanceof Player){
                defender = (Player) selectedPlayer.player;
                circle = selectedPlayer.circle;
                
                result.add(new PlayerInfo(defender.getAttack(), defender.getStamina(), defender.getDefence(),
                new ExactPosition(circle.getCenterX(), circle.getCenterY())));
            }
        }
        
        return result;
    }
    
    public ArrayList<PlayerInfo> getMidfielders(){
        
        ArrayList<PlayerInfo> result = new ArrayList<>();
        
        Player midfielder;
        Circle circle;
        
        for (PlayerIndex selectedPlayer : selectedPlayers) {
            if(selectedPlayer.circle.getCenterX() >= 358 && selectedPlayer.circle.getCenterX() < 663 && selectedPlayer.player instanceof Player){
                midfielder = (Player) selectedPlayer.player;
                circle = selectedPlayer.circle;
                
                result.add(new PlayerInfo(midfielder.getAttack(), midfielder.getStamina(), midfielder.getDefence(),
                new ExactPosition(circle.getCenterX(), circle.getCenterY())));
            }
        }
        
        return result;
    }
    
    public ArrayList<PlayerInfo> getAttackers(){
        
        ArrayList<PlayerInfo> result = new ArrayList<>();
        
        Player attacker;
        Circle circle;
        
        for (PlayerIndex selectedPlayer : selectedPlayers) {
            if(selectedPlayer.circle.getCenterX() >= 663 && selectedPlayer.player instanceof Player){
                attacker = (Player) selectedPlayer.player;
                circle = selectedPlayer.circle;
                
                result.add(new PlayerInfo(attacker.getAttack(), attacker.getStamina(), attacker.getDefence(),
                new ExactPosition(circle.getCenterX(), circle.getCenterY())));
            }
        }
        
        return result;
    }
    
    public boolean checkValid(){
        return selectedPlayers.size() == 11;
    }
}
