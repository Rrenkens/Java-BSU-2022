package by.dudkoandrei.quizer.exceptions;

public class QuizFinishedException extends RuntimeException {

  public QuizFinishedException() {
    super("Quiz is finished");
  }
}
