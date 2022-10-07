package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.task_generators.TaskGenerator;

public interface MathTaskGenerator extends TaskGenerator {
    double getMinNumber(); // получить минимальное число

    double getMaxNumber(); // получить максимальное число

    int getPrecision();

    default double getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }

    default double Round(double value) {
        double scale = Math.pow(10, getPrecision());
        return Math.round(value * scale) / scale;
    }
}
