package by.Adamenko.quizer.tasks;

import by.Adamenko.quizer.Result;

public interface Task {
    String getText();
    Result validate(String answer);
}
