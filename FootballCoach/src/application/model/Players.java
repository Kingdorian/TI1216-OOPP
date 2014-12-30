package application.model;

public abstract class Players {

    private static int id;
    private int playerid;
    private String name;
    private String surname;
    private int number;

    private Status status;
    private int timeNotAvailable;
    private Reason reason;

    /**
     * Constructor
     *
     * @param id Players unique ID.
     * @param name First name of the player.
     * @param surname Surname of the player.
     * @param number Backnumber of the player.
     * @param status Status if player is injured or suspended or both.
     * @param timeNotAvailable Time that the player isn't available.
     * @param reason Reason why the player is injured.
     */
    public Players(String name, String surname, int number, Status status, int timeNotAvailable, Reason reason) {

        this.playerid = id;
        id++;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.status = status;
        this.timeNotAvailable = timeNotAvailable;
        this.reason = reason;

    }

    public abstract double getAbility();

    public abstract String getKind();

    public boolean equals(Object other) {
        if (other instanceof Players) {
            Players p = (Players) other;
            if (this.name.equals(p.getName())
                    && this.surname.equals(p.getSurName())
                    && this.number == p.getNumber()
                    && this.status == p.getStatus()
                    && this.timeNotAvailable == p.getTimeNotAvailable()
                    && this.reason == p.getReason()) {
                return true;
            }
        }

        return false;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surname;
    }

    public void setSurName(String surName) {
        this.surname = surName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTimeNotAvailable() {
        return timeNotAvailable;
    }

    public void setTimeNotAvailable(int timeNotAvailable) {
        this.timeNotAvailable = timeNotAvailable;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public String toString() {
        return "Players [id=" + id + ", name=" + name + ", surname=" + surname
                + ", number=" + number + ", status=" + status
                + ", timeNotAvailable=" + timeNotAvailable + ", reason="
                + reason + "]";
    }

    public boolean isAvailable() {
        return timeNotAvailable == 0;
    }

    /**
     * Get the estimated value of the player
     *
     * @return int price
     */
    public int getPrice() {
        double ability = this.getAbility();
        int price = (int) (20000.0 * Math.pow(Math.E, 1.2 * (ability + 1)) - 430000);
        price -= price % 10000; // set last 4 digits to 0
        return price;
    }

    /**
     * Get the ability of the player in string format, with two numbers after
     * the decimal point.
     *
     * @return a String containing the ability of the player.
     */
    public String getAbilityStr() {
        String ability = Double.toString(this.getAbility());
        String result = "";
        for (int i = 0; i < ability.length() && i < 4; i++) {
            result += ability.charAt(i);
        }
        while (result.length() < 4) {
            result += "0";
        }
        return result;
    }
}
