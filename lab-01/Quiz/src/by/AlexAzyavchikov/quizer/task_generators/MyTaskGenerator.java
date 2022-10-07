package by.AlexAzyavchikov.quizer.task_generators;

import by.AlexAzyavchikov.quizer.exceptions.IncorrectInputException;
import by.AlexAzyavchikov.quizer.exceptions.NoTasksException;
import by.AlexAzyavchikov.quizer.tasks.Task;
import by.AlexAzyavchikov.quizer.tasks.TextTask;

import java.util.*;

public class MyTaskGenerator implements TaskGenerator {
    private final int maxApples;

    public MyTaskGenerator(int maxApples) {
        this.maxApples = maxApples;
    }

    public Task generate() {
        if (maxApples <= 0) {
            throw new IncorrectInputException("maxApples(" + maxApples + ") <= 0");
        }
        int apples = (int) (Math.random() * maxApples + 1);
        int stolenApples = (int) (Math.random() * apples);
        return new TextTask("You have " + apples + " apples. I take " + stolenApples + " of them. How many apples do you have?",
                String.valueOf(apples - stolenApples));
    }
}
