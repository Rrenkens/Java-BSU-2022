package by.polina_kostyukovich.quizer.exceptions;

public class TooFewGeneratorsException extends RuntimeException {
    public TooFewGeneratorsException() {
        super();
    }

    public TooFewGeneratorsException(String message) {
        super(message);
    }
}
