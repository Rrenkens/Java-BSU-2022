package by.dudkoandrei.quizer.exceptions;

public class NoMoreTasksException extends RuntimeException {

  public NoMoreTasksException() {
    super("No more tasks");
  }
}
