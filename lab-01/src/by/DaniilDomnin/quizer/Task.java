package by.DaniilDomnin.quizer;

public interface Task {
    String getText();
    Result validate(String answer);
}