/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.ContainerPackage;

/**
 *
 * @author faris
 */
public final class PlayerInfo {
    
//    public int MAX_SPEED = 150;
//    public int WALK_SPEED = 50;
//    public int SHOOT_SPEED = 800;
    
    private final int attackPower;
    private final int stamina;
    private final int defensePower;
    private final ExactPosition favoritePosition;
    private final int penaltyStopPower;
    private final int stopPower;

    
    public PlayerInfo(int stopPower, int penaltyStopPower, ExactPosition favoritePosition){
        this.stopPower = stopPower;
        this.penaltyStopPower = penaltyStopPower;
        this.favoritePosition = favoritePosition;
        this.attackPower = 0;
        this.stamina = 0;
        this.defensePower = 0;
    }

    public PlayerInfo(int attackPower, int stamina, int defensePower, ExactPosition favoritePosition) {
        this.attackPower = attackPower;
        this.stamina = stamina;
        this.defensePower = defensePower;
        this.favoritePosition = favoritePosition;
        this.stopPower = 0;
        this.penaltyStopPower = 0;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getStamina() {
        return stamina;
    }
    
    public int getDefensePower() {
        return defensePower;
    }

    public ExactPosition getFavoritePosition() {
        return favoritePosition;
    }

    public int getPenaltyStopPower() {
        return penaltyStopPower;
    }

    public int getStopPower() {
        return stopPower;
    }

}
