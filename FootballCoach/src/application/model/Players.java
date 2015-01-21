package application.model;

public abstract class Players {

    private static int id;
    private String name;
    private String surname;
    private int number;

    private Card card;
    private int timeNotAvailable;
    private Reason reason;

    /**
     * Constructor
     *
     * @param name First name of the player.
     * @param surname Surname of the player.
     * @param number Backnumber of the player.
     * @param status Card if player is injured or suspended or both.
     * @param timeNotAvailable Time that the player isn't available.
     * @param reason Reason why the player is injured.
     */
    public Players(String name, String surname, int number, Card status, int timeNotAvailable, Reason reason) {

        id++;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.card = status;
        this.timeNotAvailable = timeNotAvailable;
        this.reason = reason;

    }

    /**
     * Get the ability of the player
     *
     * @return the ability of the player
     */
    public abstract double getAbility();

    /**
     * Get the kind of the player
     *
     * @return the kind of the player
     */
    public abstract String getKind();

    /**
     * Equals method with player parameter
     *
     * @param p the player to compare this player to
     * @return if the players are equal
     */
    public boolean equals(Players p) {
        return this.name.equals(p.getName())
                && this.surname.equals(p.getSurName())
                && this.number == p.getNumber()
                && this.card == p.getCard()
                && this.timeNotAvailable == p.getTimeNotAvailable()
                && this.reason == p.getReason();

    }

    /**
     * Get the ID of this player
     *
     * @return the ID of this player
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of this player
     *
     * @param id the ID of the player
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name of this player
     *
     * @return the name of this player
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this player
     *
     * @param name the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the surname of this player
     *
     * @return the surname of the player
     */
    public String getSurName() {
        return surname;
    }

    /**
     * Set the surname of this palyer
     *
     * @param surName the surname of this player
     */
    public void setSurName(String surName) {
        this.surname = surName;
    }

    /**
     * Get the number of this player
     *
     * @return the number of this player
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set the number of this player
     *
     * @param number the number of this player
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get the kind of card this player has
     *
     * @return the card of this player
     */
    public Card getCard() {
        return card;
    }

    /**
     * Set the kind of card this player has
     *
     * @param card the card
     */
    public void setCard(Card card) {
        if (this.card != Card.DEFAULT && card == Card.YELLOW) {

            this.card = Card.RED;
        } else {
            this.card = card;
        }
    }

    /**
     * Get the time this player is not available
     *
     * @return the time this player is not available
     */
    public int getTimeNotAvailable() {
        return timeNotAvailable;
    }

    /**
     * Set the time this player is not available
     *
     * @param timeNotAvailable the time this player is not available
     */
    public void setTimeNotAvailable(int timeNotAvailable) {
        this.timeNotAvailable = timeNotAvailable;
    }

    /**
     * Get the reason this player is injured
     *
     * @return the reason
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * Set the reason this player is injured
     *
     * @param reason the reason
     */
    public void setReason(Reason reason) {
        this.reason = reason;
    }

    /**
     * Convert this class to a string
     *
     * @return a string containing information about this class
     */
    @Override
    public String toString() {
        return "Players [id=" + id + ", name=" + name + ", surname=" + surname
                + ", number=" + number + ", status=" + card
                + ", timeNotAvailable=" + timeNotAvailable + ", reason="
                + reason + "]";
    }

    /**
     * Get if this player is available
     *
     * @return if this player is available
     */
    public boolean isAvailable() {
    	return this.timeNotAvailable==0 || this.card == Card.YELLOW;
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

    /**
     * reset the cards and injury for the player
     */
    public void resetCardReason() {
        card = Card.DEFAULT;
        reason = Reason.DEFAULT;
        timeNotAvailable = 0;
    }
}
