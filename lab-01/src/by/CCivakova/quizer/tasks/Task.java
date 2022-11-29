package by.CCivakova.quizer.tasks;

import by.CCivakova.quizer.Result;
public interface Task {
    interface Generator {
        Task generate();
    }

    String getText();

    Result validate(String answer);
}