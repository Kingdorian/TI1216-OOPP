package application.model;

public enum Card {

    DEFAULT(0),
    YELLOW(1),
    RED(2);

    Card(int card) {
        this.card = card;
    }

    private final int card;
}
