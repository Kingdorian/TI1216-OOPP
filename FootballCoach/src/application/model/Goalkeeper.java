package application.model;

public class Goalkeeper extends Players {

    private int stopPower, penaltyStopPower;

    /**
     * Constructor
     *
     * @param surName the surname of the player
     * @param name First name of the player.
     * @param number Backnumber of the player.
     * @param status Card if player is injured or suspended or both.
     * @param timeNotAvailable Time that the player isn't available.
     * @param reason Reason why the player is injured.
     * @param stopPower Skill of the goalkeeper to stop goals.
     * @param penaltyStopPower Skill of the goalkeeper to stop penalty's.
     */
    public Goalkeeper(String name, String surName, int number, Card status, int timeNotAvailable, Reason reason, int stopPower, int penaltyStopPower) {
        super(name, surName, number, status, timeNotAvailable, reason);

        this.stopPower = stopPower;
        this.penaltyStopPower = penaltyStopPower;

    }

    /**
     * Get the stop power of the keeper
     *
     * @return the stop power
     */
    public int getStopPower() {
        return this.stopPower;
    }

    /**
     * Set the stop power of the keeper
     *
     * @param stopPower the stop power
     */
    public void setStopPower(int stopPower) {
        this.stopPower = stopPower;
    }

    /**
     * Get the penalty stop power of the keeper
     *
     * @return the penalty stop power
     */
    public int getPenaltyStopPower() {
        return penaltyStopPower;
    }

    /**
     * Set the penalty stop power of the keeper
     *
     * @param penaltyStopPower the penalty stop power
     */
    public void setPenaltyStopPower(int penaltyStopPower) {
        this.penaltyStopPower = penaltyStopPower;
    }

    /**
     * Convert this class to a string
     *
     * @return a string containing information about this class
     */
    @Override
    public String toString() {
        return "Goalkeeper [stopPower=" + stopPower + ", penaltyStopPower="
                + penaltyStopPower + ", " + super.toString() + "]";
    }

    /**
     * Equals method
     *
     * @param obj object to compare to
     * @return if this and obj are equal
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Goalkeeper)) {
            return false;
        }
        Goalkeeper other = (Goalkeeper) obj;
        if (this.penaltyStopPower != other.getPenaltyStopPower()) {
            return false;
        }
        if (this.stopPower != other.getStopPower()) {
            return false;
        }
        return true;
    }

    /**
     * Get the kind fo the goalkeeper (goalkeeper)
     *
     * @return string: "Goalkeeper"
     */
    @Override
    public String getKind() {
        return "Goalkeeper";
    }

    /**
     * Get the ability of this goalkeeper
     *
     * @return the ability
     */
    @Override
    public double getAbility() {
        return stopPower * 0.04 + penaltyStopPower / 100.0;
    }
}
