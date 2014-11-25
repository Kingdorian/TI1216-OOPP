/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContainerPackage;

import java.util.ArrayList;

/**
 *
 * @author faris
 */
public class TeamInfo {
    
    private ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();
    
    /**
     * basic constructor
     */
    public TeamInfo() {
    }

    /**
     * adds a player to the team
     * @param playerInfoList 
     */
    public void addPlayers(PlayerInfo playerInfoList) {
        this.playerInfoList.add(playerInfoList);
    }

    /**
     * gives all players in the team
     * @return an ArrayList of PlayerInfo objects of all the players in the team
     */
    public ArrayList<PlayerInfo> getPlayerInfoList() {
        return playerInfoList;
    }

    /**
     * gives all players in the base
     * @return an ArrayList of PlayerInfo objects of all the players in the base
     */
    public ArrayList<PlayerInfo> getPlayersInBase() {
        ArrayList<PlayerInfo> res = new ArrayList<>();
        for(PlayerInfo pl : playerInfoList)
            if(pl.isBasePlayer())
                res.add(pl);
        
        return res;
    }
}
