package by.polina_kostyukovich.quzer.tasks;

import by.polina_kostyukovich.quzer.Result;

public interface Task {
    interface Generator {
        Task generate();
    }

    String getText();

    String getAnswer();

    Result validate(String answer);
}