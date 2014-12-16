package application.model;

public enum Status {

    DEFAULT(0),
    YELLOW(1),
    RED(2);


    private Status(int status) {
        this.status = status;
    }

    int status;
}
