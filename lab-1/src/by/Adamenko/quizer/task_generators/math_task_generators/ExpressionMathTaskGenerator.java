package by.Adamenko.quizer.task_generators.math_task_generators;

import by.Adamenko.quizer.tasks.math_tasks.ExpressionMathTask;
import by.Adamenko.quizer.tasks.math_tasks.Operator;

import java.util.EnumSet;

public class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {
    public ExpressionMathTaskGenerator(
            int minNumber,
            int maxNumber,
            EnumSet<Operator> operators
    ) {
        initialize(minNumber, maxNumber, operators);
    }

    public ExpressionMathTask generate() {
        // TODO trow
        return new ExpressionMathTask(rnd.nextInt(min, max + 1),
                rnd.nextInt(min, max + 1),
                operatorArrayList.get(rnd.nextInt(0, operatorArrayList.size())));
    }
}
