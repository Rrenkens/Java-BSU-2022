package by.polina_kostyukovich.quizer.exceptions;

public class QuizNotFinishedException extends RuntimeException {
    public QuizNotFinishedException() {
        super("Quiz is not finished");
    }
}
