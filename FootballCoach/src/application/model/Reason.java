package application.model;

/**
 * Enum for the reason why this player is injured
 * @author Faris, Sander
 */
public enum Reason {

    DEFAULT(0),
    HAMSTRING(1),
    KNEE(2),
    HEAD(3),
    ANKEL(4);

    Reason(int reason) {
        this.reason = reason;
    }

    int reason;

    /**
     * Turn the reason into a string
     * @return string of the reason
     */
    @Override
    public String toString() {
        switch (reason) {
            case 1:
                return "HAMSTRING";
            case 2:
                return "KNEE";
            case 3:
                return "HAMSTRING";
            case 4:
                return "HEAD";
            default:
                return "DEFAULT";
        }
    }
    
    /**
     * Generate a random Reason
     * @return a random Reason
     */
    public Reason random(){
        switch ((int) (Math.random() * 4 + 1)) {
            case 1:
                return HAMSTRING;
            case 2:
                return KNEE;
            case 3:
                return HEAD;
            case 4:
                return ANKEL;
            default:
                return DEFAULT;
        }
    }
}
