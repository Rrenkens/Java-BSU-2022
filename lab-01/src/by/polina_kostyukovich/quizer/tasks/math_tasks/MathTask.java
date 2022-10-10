package by.polina_kostyukovich.quizer.tasks.math_tasks;

import by.polina_kostyukovich.quizer.tasks.Task;

public interface MathTask extends Task {
    interface Generator extends Task.Generator {
        int getMinNumber();

        int getMaxNumber();

        /**
         * @return разница между максимальным и минимальным возможным числом
         */
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
