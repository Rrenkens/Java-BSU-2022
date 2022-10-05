package by.Adamenko.quizer.task_generators;

import by.Adamenko.quizer.Operator;
import by.Adamenko.quizer.tasks.EquationTask;

import java.util.ArrayList;
import java.util.Random;

public class EquationTaskGenerator implements TaskGenerator {
    private final int min;
    private final int max;
    private ArrayList<Operator> operators;
    private final Random rnd = new Random();

    public EquationTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        min = minNumber;
        max = maxNumber;
        operators = new ArrayList<Operator>();
        if (generateDifference) operators.add(Operator.Minus);
        if (generateSum) operators.add(Operator.Plus);
        if (generateMultiplication) operators.add(Operator.Multiple);
        if (generateDivision) operators.add(Operator.Divide);
    }

    public EquationTask generate() {
        // trow
        int pos = rnd.nextInt(0, operators.size());
        return new EquationTask(rnd.nextInt(min, max + 1),
                                rnd.nextInt(min, max + 1),
                                operators.get(pos));
    }
}
