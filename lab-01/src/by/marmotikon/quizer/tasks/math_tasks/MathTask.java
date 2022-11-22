package by.marmotikon.quizer.tasks.math_tasks;

import by.marmotikon.quizer.tasks.Task;

/**
 * Interface, который описывает одно математическое задание
 */
public interface MathTask extends Task {
    interface MathTaskGenerator extends TaskGenerator {
        /**
         * @return минимальное генерируемое число
         */
        double getMinNumber();

        /**
         * @return максимальное генерируемое число
         */
        double getMaxNumber();

        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }

    /**
     * Enum, который описывает математическую операцию в задании
     */
    enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }
}
