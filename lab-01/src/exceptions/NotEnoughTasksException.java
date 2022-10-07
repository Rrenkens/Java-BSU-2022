package exceptions;

public class NotEnoughTasksException extends RuntimeException {

    public NotEnoughTasksException(String s) {
        super(s);
    }
}
