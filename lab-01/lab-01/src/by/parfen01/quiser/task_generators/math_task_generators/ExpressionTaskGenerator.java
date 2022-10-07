package by.parfen01.quiser.task_generators.math_task_generators;

import by.parfen01.quiser.tasks.math_tasks.ExpressionTask;
import by.parfen01.quiser.tasks.math_tasks.MathTask;
import java.util.EnumSet;

class ExpressionTaskGenerator extends AbstractMathTaskGenerator {
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     */
    ExpressionTaskGenerator(int minNumber,
                            int maxNumber,
                            EnumSet<MathTask.Operation> operations) {
        super(minNumber, maxNumber, operations);
    }

    /**
     * return задание типа {@link ExpressionTask}
     */
    public ExpressionTask generate() {
        return new ExpressionTask(getRandomNumberForTask(), getRandomNumberForTask(), getRandomOperationFromSet());
    }
}
