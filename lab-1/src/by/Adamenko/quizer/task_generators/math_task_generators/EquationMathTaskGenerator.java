package by.Adamenko.quizer.task_generators.math_task_generators;

import by.Adamenko.quizer.Operator;
import by.Adamenko.quizer.task_generators.TaskGenerator;
import by.Adamenko.quizer.tasks.math_tasks.AbstractMathTask;
import by.Adamenko.quizer.tasks.math_tasks.EquationMathTask;

import java.util.ArrayList;
import java.util.Random;

public class EquationMathTaskGenerator extends AbstractMathTaskGenerator {
    public EquationMathTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
       initialize(minNumber, maxNumber, generateSum, generateDifference, generateMultiplication, generateDivision);
    }

    public EquationMathTask generate() {
        // TODO trow
        int pos = rnd.nextInt(0, operators.size());
        return new EquationMathTask(rnd.nextInt(min, max + 1),
                                rnd.nextInt(min, max + 1),
                                operators.get(pos));
    }
}
