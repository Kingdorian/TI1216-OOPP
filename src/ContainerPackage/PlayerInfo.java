/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContainerPackage;

/**
 *
 * @author faris
 */
public final class PlayerInfo {
    
    private String name;
    private int playerNumber; 
    private boolean isBasePlayer;
    
    public int MAX_SPEED = 150;
    public int WALK_SPEED = 50;
    public int SHOOT_SPEED = 800;
    
    /**
     * basic constructor, creating a non-existent player
     */
    public PlayerInfo(){
        name = "missing name";
        playerNumber = -1;
    }

    /**
     * constructor containing all needed info
     * @param name          name of the player
     * @param playerNumber  shirt number of the player
     * @param isBasePlayer  boolean containing if the player is part of the teams base composition
     */
    public PlayerInfo(String name, int playerNumber, boolean isBasePlayer) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.isBasePlayer = isBasePlayer;
    }

    public String getName() {
        return name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean isBasePlayer() {
        return isBasePlayer;
    }
    
}
