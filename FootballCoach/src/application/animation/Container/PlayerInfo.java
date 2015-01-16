/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.Container;

/**
 * This class stores the abilities of a player.
 *
 * @author Faris
 */
public final class PlayerInfo {

//    public int MAX_SPEED = 150;
//    public int WALK_SPEED = 50;
//    public int SHOOT_SPEED = 800;
    private final int attackPower;
    private final int stamina;
    private final int defensePower;
    private final Position favoritePosition;
    private final int penaltyStopPower;
    private final int stopPower;

    /**
     * Constructor for a goalkeeper.
     *
     * @param stopPower goalkeepers stop power
     * @param penaltyStopPower goalkeepers penalty stop power
     * @param favoritePosition goalkeepers favorite position
     */
    public PlayerInfo(int stopPower, int penaltyStopPower, Position favoritePosition) {
        this.stopPower = stopPower;
        this.penaltyStopPower = penaltyStopPower;
        this.favoritePosition = favoritePosition;
        this.attackPower = 0;
        this.stamina = 0;
        this.defensePower = 0;
    }

    /**
     * Constructor for a field player.
     *
     * @param attackPower players attack power
     * @param stamina players stamina
     * @param defensePower players defense power
     * @param favoritePosition players favorite position
     */
    public PlayerInfo(int attackPower, int stamina, int defensePower, Position favoritePosition) {
        this.attackPower = attackPower;
        this.stamina = stamina;
        this.defensePower = defensePower;
        this.favoritePosition = favoritePosition;
        this.stopPower = 0;
        this.penaltyStopPower = 0;
    }

    /**
     * Get the players attack power
     *
     * @return the attack power
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Get the players stamina
     *
     * @return the stamina
     */
    public int getStamina() {
        return stamina;
    }

    /**
     * Get the players defense power
     *
     * @return the defense power
     */
    public int getDefensePower() {
        return defensePower;
    }

    /**
     * Get the players favorite position
     *
     * @return the players favorite position
     */
    public Position getFavoritePosition() {
        return favoritePosition;
    }

    /**
     * Get the players penalty stop power
     *
     * @return the penalty stop power
     */
    public int getPenaltyStopPower() {
        return penaltyStopPower;
    }

    /**
     * Get the players stop power
     *
     * @return the stop power
     */
    public int getStopPower() {
        return stopPower;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" + "attackPower=" + attackPower + ", stamina=" + stamina + ", defensePower=" + defensePower + ", favoritePosition=" + favoritePosition + ", penaltyStopPower=" + penaltyStopPower + ", stopPower=" + stopPower + '}';
    }
}
