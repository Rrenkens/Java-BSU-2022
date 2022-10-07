package by.AlexAzyavchikov.quizer.tasks;

import by.AlexAzyavchikov.quizer.Result;

/**
 * Interface, который описывает одно задание
 */
public interface Task {
    public String getText();

    Result validate(String answer);
}
