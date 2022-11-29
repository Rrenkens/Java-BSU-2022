package by.CCivakova.quizer.tasks.math_tasks;

import by.CCivakova.quizer.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public interface MathTask  extends Task {
    interface Generator extends Task.Generator {
        int getMinNumber();

        int getMaxNumber();

        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        };
    }

    enum Operation {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION
    }
}
