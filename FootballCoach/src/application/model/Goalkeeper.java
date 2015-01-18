package application.model;

public class Goalkeeper extends Players {

    private int stopPower, penaltyStopPower;

    /**
     * Constructor
     *
     * @param id Players unique ID.
     * @param name First name of the player.
     * @param surname Surname of the player.
     * @param number Backnumber of the player.
     * @param status Card if player is injured or suspended or both.
     * @param timeNotAvailable Time that the player isn't available.
     * @param reason Reason why the player is injured.
     * @param stopPower Skill of the goalkeeper to stop goals.
     * @param penalyStopPower Skill of the goalkeeper to stop penalty's.
     */
    public Goalkeeper(String name, String surName, int number, Card status, int timeNotAvailable, Reason reason, int stopPower, int penaltyStopPower) {
        super(name, surName, number, status, timeNotAvailable, reason);

        this.stopPower = stopPower;
        this.penaltyStopPower = penaltyStopPower;

    }

    public int getStopPower() {
        return this.stopPower;
    }

    public void setStopPower(int stopPower) {
        this.stopPower = stopPower;
    }

    public int getPenaltyStopPower() {
        return penaltyStopPower;
    }

    public void setPenaltyStopPower(int penaltyStopPower) {
        this.penaltyStopPower = penaltyStopPower;
    }

    public String toString() {
        return "Goalkeeper [stopPower=" + stopPower + ", penaltyStopPower="
                + penaltyStopPower + ", " + super.toString() + "]";
    }

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

    @Override
    public String getKind() {
        return "Goalkeeper";
    }

    @Override
    public double getAbility() {
        return stopPower * 0.04 + penaltyStopPower / 100.0;
    }
}
