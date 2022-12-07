package by.dudkoandrei.quizer.exceptions;

public class GeneratorsFailedException extends RuntimeException {

  public GeneratorsFailedException() {
    super("All generators threw exceptions");
  }
}
