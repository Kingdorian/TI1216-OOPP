package application.model;

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
     * @param status Card if player is injured or suspended or both.
     * @param timeNotAvailable Time that the player isn't available.
     * @param reason Reason why the player is injured.
     * @param attack Attack skill of the player.
     * @param defence Defence skill of the player.
     * @param stamina Stamina of the players.
     */
    public Player(String name, String surName, int number, Card status,
            int timeNotAvailable, Reason reason, int attack, int defence, int stamina) {
        super(name, surName, number, status, timeNotAvailable, reason);
        this.attack = attack;
        this.defence = defence;
        this.stamina = stamina;

    }

    /**
     * Get the attack power
     *
     * @return the attack power
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Set the attack power
     *
     * @param attack the attack power
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * Get the defense power
     *
     * @return the defense power
     */
    public int getDefence() {
        return defence;
    }

    /**
     * Set the defense power
     *
     * @param defence the defense power
     */
    public void setDefence(int defence) {
        this.defence = defence;
    }

    /**
     * Get the stamina
     *
     * @return the stamina
     */
    public int getStamina() {
        return stamina;
    }

    /**
     * Set the stamina
     *
     * @param stamina the stamina
     */
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    /**
     * Convert this class to a string
     *
     * @return a string containing information about this class
     */
    @Override
    public String toString() {
        return "Player [attack=" + attack + ", defence=" + defence
                + ", stamina=" + stamina + super.toString() + "]";
    }

    /**
     * Equals method
     *
     * @param obj object to compare to
     * @return if this is equal to obj
     */
    @Override
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

    /**
     * Get the ability of the player
     *
     * @return the ablitiy of the player
     */
    @Override
    public double getAbility() {
        return defence > attack ? ((int) (defence + stamina + 0.1 * attack) * 5.0 / 2.1) / 100.0
                : ((int) (attack + stamina + 0.1 * defence) * 5.0 / 2.1) / 100.0; // return a double with 2 numbers after the decimal point
    }

    /**
     * Get the kind of the player
     *
     * @return the kind of the player
     */
    @Override
    public String getKind() {
        if (attack > 75 && defence > 75) {
            return "Allrounder";
        }
        if (attack > defence + 20) {
            return "Forward";
        } else if (defence > attack + 20) {
            return "Defender";
        } else {
            return "Midfielder";
        }
    }
}
