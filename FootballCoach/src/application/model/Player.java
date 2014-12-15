package application.model;

import java.text.DecimalFormat;

public class Player extends Players {

    int attack;
    int defence;
    int stamina;

    /**
     * Constructor
     *
     * @param name First name of the player.
     * @param surName Surname of the player.
     * @param number Backnumber of the player.
     * @param status Status if player is injured or suspended or both.
     * @param timeNotAvailable Time that the player isn't available.
     * @param reason Reason why the player is injured.
     * @param attack Attack skill of the player.
     * @param defence Defence skill of the player.
     * @param stamina Stamina of the players.
     */
    public Player(String name, String surName, int number, Status status,
            int timeNotAvailable, Reason reason, int attack, int defence, int stamina) {
        super(name, surName, number, status, timeNotAvailable, reason);
        this.attack = attack;
        this.defence = defence;
        this.stamina = stamina;

    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public String toString() {
        return "Player [attack=" + attack + ", defence=" + defence
                + ", stamina=" + stamina + super.toString() + "]";
    }

    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player other = (Player) obj;
            if (super.equals(other)
                    && this.attack == other.getAttack()
                    && this.defence == other.getDefence()
                    && this.stamina == other.getStamina()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public double getAbility(){
        return defence > attack ? ((int)(defence + stamina + 0.1*attack) * 5.0 / 2.1) / 100.0
                                : ((int)(attack + stamina + 0.1*defence) * 5.0 / 2.1) / 100.0; // return a double with 2 numbers after the decimal point
    }
    
    @Override
    public String getKind(){
        if(attack > 75 && defence > 75)
            return "Allrounder";
        if(attack > defence + 20)
            return "Forward";
        else if(defence > attack + 20)
            return "Defender";
        else
            return "Midfielder";
    }
}
