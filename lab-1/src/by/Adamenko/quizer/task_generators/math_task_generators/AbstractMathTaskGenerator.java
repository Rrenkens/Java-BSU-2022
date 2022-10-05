package by.Adamenko.quizer.task_generators.math_task_generators;

import by.Adamenko.quizer.Operator;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    protected int min;
    protected int max;
    protected ArrayList<Operator> operators;
    protected final Random rnd = new Random();

    void initialize(int minNumber,
                    int maxNumber,
                    boolean generateSum,
                    boolean generateDifference,
                    boolean generateMultiplication,
                    boolean generateDivision) {
        min = minNumber;
        max = maxNumber;
        operators = new ArrayList<Operator>();
        if (generateDifference) operators.add(Operator.Minus);
        if (generateSum) operators.add(Operator.Plus);
        if (generateMultiplication) operators.add(Operator.Multiple);
        if (generateDivision) operators.add(Operator.Divide);
    }

    @Override
    public int getMinNumber() {
        return min;
    }

    @Override
    public int getMaxNumber() {
        return max;
    }

}
