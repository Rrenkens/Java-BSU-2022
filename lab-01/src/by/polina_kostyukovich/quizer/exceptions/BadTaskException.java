package by.polina_kostyukovich.quizer.exceptions;

public class BadTaskException extends RuntimeException {
    public BadTaskException() {
        super();
    }

    public BadTaskException(String message) {
        super(message);
    }
}
