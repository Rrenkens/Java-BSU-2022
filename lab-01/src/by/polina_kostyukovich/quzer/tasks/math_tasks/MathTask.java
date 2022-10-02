package by.polina_kostyukovich.quzer.tasks.math_tasks;

import by.polina_kostyukovich.quzer.tasks.Task;

public interface MathTask extends Task {
    interface Generator extends Task.Generator {
        int getMinNumber();

        int getMaxNumber();

        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }

    enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }
}
