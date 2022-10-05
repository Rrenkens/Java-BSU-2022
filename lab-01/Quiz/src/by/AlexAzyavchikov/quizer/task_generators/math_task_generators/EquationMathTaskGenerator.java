package by.AlexAzyavchikov.quizer.task_generators.math_task_generators;

import by.AlexAzyavchikov.quizer.Task;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.EquationMathTask;
import by.AlexAzyavchikov.quizer.tasks.math_tasks.MathTask;

import java.util.EnumSet;

public class EquationMathTaskGenerator extends AbstractMathGenerator {
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

    public EquationMathTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<MathTask.Operator> operators
    ) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        if (maxNumber < minNumber) {
            throw new IllegalArgumentException("In EquationMathTaskGenerator maxNumber(" + String.valueOf(maxNumber)
                    + ") < minNumber(" + String.valueOf(minNumber) + ") !");
        }
        this.operators = operators;
    }

    /**
     * return задание типа {@link EquationMathTask}
     */
    public EquationMathTask generate() {
        assert !operators.isEmpty();
//        return new EquationMathTask(GenerateNumber(), GenerateOperator(), GenerateNumber());
        return null;
    }

}
