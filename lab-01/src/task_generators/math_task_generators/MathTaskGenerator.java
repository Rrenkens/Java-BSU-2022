package task_generators.math_task_generators;

import by.DaniilDomnin.quizer.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    int getMinNumber(); // получить минимальное число
    int getMaxNumber(); // получить максимальное число

    default int getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }

    enum Operation {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }
}
