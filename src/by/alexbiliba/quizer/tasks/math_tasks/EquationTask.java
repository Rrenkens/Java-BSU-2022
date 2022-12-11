package by.alexbiliba.quizer.tasks.math_tasks;

import by.alexbiliba.quizer.Result;
import by.alexbiliba.quizer.Task;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTask extends AbstractMathTask {
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
         * return задание типа {@link EquationTask}
         */
        public EquationTask generate() {
            AbstractMathTask newTask = super.generate();
            return new EquationTask(newTask, newTask.getAnswerPosition());
        }
    }
    public String getAnswer() {
        return answer;
    }
    public EquationTask(String text, String answer) {
        super(text, answer);
    }

    public EquationTask(AbstractMathTask taskData, int answerPosition) {
        super(taskData.getLeftNumber(),
                taskData.getRightNumber(),
                taskData.getResultNumber(),
                taskData.getOperation(),
                taskData.getPrecision(),
                answerPosition);
    }
}
