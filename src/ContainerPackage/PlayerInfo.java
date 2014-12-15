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
    
//    public int MAX_SPEED = 150;
//    public int WALK_SPEED = 50;
//    public int SHOOT_SPEED = 800;
    
    private int attackPower;
    private int stamina;
    private int defensePower;
    private ExactPosition favoritePosition;
    
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

    /**
     * constructor for testing
     * @param attackPower
     * @param stamina
     * @param defensePower 
     * @param favoritePosition 
     */
    public PlayerInfo(int attackPower, int stamina, int defensePower, ExactPosition favoritePosition) {
        this.attackPower = attackPower;
        this.stamina = stamina;
        this.defensePower = defensePower;
        this.favoritePosition = favoritePosition;
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

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public void setDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    public ExactPosition getFavoritePosition() {
        return favoritePosition;
    }
    
    
}
