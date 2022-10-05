package by.Adamenko.quizer.task_generators.math_task_generators;

import by.Adamenko.quizer.task_generators.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    int getMinNumber();
    int getMaxNumber();

    default int getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }
}
