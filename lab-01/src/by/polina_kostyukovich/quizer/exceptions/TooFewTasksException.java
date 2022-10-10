package by.polina_kostyukovich.quizer.exceptions;

public class TooFewTasksException extends RuntimeException {
    public TooFewTasksException() {
        super();
    }

    public TooFewTasksException(String message) {
        super(message);
    }
}
