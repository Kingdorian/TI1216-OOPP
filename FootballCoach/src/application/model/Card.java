package application.model;

/**
 * Enum for cards (yellow, red, or none)
 * @author faris
 */
public enum Card {

    DEFAULT(0),
    YELLOW(1),
    RED(2);

    /**
     * Construcotr
     * @param card id of the card
     */
    Card(int card) {
        this.card = card;
    }

    private final int card;
}
