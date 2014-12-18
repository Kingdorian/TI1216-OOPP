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
    
//    public int MAX_SPEED = 150;
//    public int WALK_SPEED = 50;
//    public int SHOOT_SPEED = 800;
    
    private final int attackPower;
    private final int stamina;
    private final int defensePower;
    private final ExactPosition favoritePosition;


    public PlayerInfo(int attackPower, int stamina, int defensePower, ExactPosition favoritePosition) {
        this.attackPower = attackPower;
        this.stamina = stamina;
        this.defensePower = defensePower;
        this.favoritePosition = favoritePosition;
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
    
    
}
