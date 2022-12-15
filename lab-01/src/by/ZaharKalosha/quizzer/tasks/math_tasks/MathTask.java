package by.ZaharKalosha.quizzer.tasks.math_tasks;

import by.ZaharKalosha.quizzer.Task;

import java.util.EnumSet;

public interface MathTask extends Task {
    enum Operation {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION
    }

    interface Generator extends Task.Generator {
        double getMinNumber();

        double getMaxNumber();

        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }

        int getPrecision();

        EnumSet<Operation> getOperations();
    }
}