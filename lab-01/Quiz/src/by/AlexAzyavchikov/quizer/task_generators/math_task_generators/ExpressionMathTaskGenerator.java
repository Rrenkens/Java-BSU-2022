package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.tasks.math_tasks.ExpressionMathTask;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask;

import java.util.*;

public class ExpressionMathTaskGenerator extends AbstractMathGenerator {
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
    boolean generateSum;
    boolean generateDifference;
    boolean generateMultiplication;
    boolean generateDivision;

    public ExpressionMathTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<MathTask.Operator> operators
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        if (maxNumber < minNumber) {
            throw new IllegalArgumentException("In ExpressionMathTaskGenerator maxNumber(" + String.valueOf(maxNumber)
                    + ") < minNumber(" + String.valueOf(minNumber) + ") !");
        }
        if (operators.isEmpty()) {
            assert false;
//            TODO: throw exception
        }
        this.operators = operators;
    }

    /**
     * return задание типа {@link ExpressionMathTask}
     */
    public ExpressionMathTask generate() {
        assert !operators.isEmpty();
        return new ExpressionMathTask(GenerateNumber(), GenerateOperator(), GenerateNumber());
    }
}
