package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    double getMinNumber(); // получить минимальное число

    double getMaxNumber(); // получить максимальное число

    int getPrecision(); // получить максимальное число

    /**
     * @return разница между максимальным и минимальным возможным числом
     */
    default double getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }
}
