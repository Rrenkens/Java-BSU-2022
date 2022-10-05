package by.Adamenko.quizer.task_generators.math_task_generators;

import by.Adamenko.quizer.tasks.math_tasks.Operator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    protected int min;
    protected int max;
    protected ArrayList<Operator> operatorArrayList = new ArrayList<>();
    protected final Random rnd = new Random();

    void initialize(int minNumber,
                    int maxNumber,
                    EnumSet<Operator> operators) {
        min = minNumber;
        max = maxNumber;
        operatorArrayList.addAll(operators);
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
