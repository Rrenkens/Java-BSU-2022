package by.Adamenko.quizer.task_generators.math_task_generators;

import by.Adamenko.quizer.Operator;
import by.Adamenko.quizer.task_generators.TaskGenerator;
import by.Adamenko.quizer.tasks.math_tasks.ExpressionMathTask;

import java.util.ArrayList;
import java.util.Random;

public class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {
    public ExpressionMathTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        initialize(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
    }

    public ExpressionMathTask generate() {
        // TODO trow
        return new ExpressionMathTask(rnd.nextInt(min, max + 1),
                rnd.nextInt(min, max + 1),
                operators.get(rnd.nextInt(0, operators.size())));
    }
}
