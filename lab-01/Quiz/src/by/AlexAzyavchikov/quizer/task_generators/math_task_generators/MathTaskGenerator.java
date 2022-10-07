package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.task_generators.TaskGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface MathTaskGenerator extends TaskGenerator {
    double getMinNumber(); // получить минимальное число

    double getMaxNumber(); // получить максимальное число

    int getPrecision();

    default double getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }
}
