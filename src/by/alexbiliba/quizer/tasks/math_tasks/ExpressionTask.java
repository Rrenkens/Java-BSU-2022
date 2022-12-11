package by.alexbiliba.quizer.tasks.math_tasks;

import by.alexbiliba.quizer.*;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {

        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param allowedOperations      разрешённые операторы для генерации (+, -, *, /)
         */
        public Generator(
                double minNumber,
                double maxNumber,
                int precision,
                EnumSet<MathTask.Operation> allowedOperations
        ) {
            super(minNumber, maxNumber, precision, 2, 0, allowedOperations);
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        public ExpressionTask generate() {
            AbstractMathTask newTask = super.generate();
            return new ExpressionTask(newTask);
        }
    }

    public ExpressionTask(String text, String answer) {
        super(text, answer);
    }

    public String getAnswer() {
        return answer;
    }
    public ExpressionTask(AbstractMathTask taskData) {
        super(taskData.getLeftNumber(),
                taskData.getRightNumber(),
                taskData.getResultNumber(),
                taskData.getOperation(),
                taskData.getPrecision(),
                2);
    }
}
