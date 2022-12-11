package by.alexbiliba.quizer.tasks.math_tasks;

import by.alexbiliba.quizer.Task;

import java.util.HashMap;

public interface MathTask extends Task {
    public interface Generator extends Task.Generator {
        double getMinNumber(); // получить минимальное число
        double getMaxNumber(); // получить максимальное число
        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }

    static double threshold = 1e-20;
    double leftValue = 0;
    double rightValue = 0;
    double resultValue = 0;

    enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }

    static HashMap<Operation, Character> operationSymbol =
            new HashMap<Operation, Character>() {{
                put(Operation.SUM, '+');
                put(Operation.DIFFERENCE, '-');
                put(Operation.MULTIPLICATION, '*');
                put(Operation.DIVISION, '/');
            }};
}
