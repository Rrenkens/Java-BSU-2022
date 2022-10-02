package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    int getMinNumber(); // получить минимальное число
    int getMaxNumber(); // получить максимальное число

    /**
     * @return разница между максимальным и минимальным возможным числом
     */
    default int getDiffNumber() {
        return 0;
    }
}
