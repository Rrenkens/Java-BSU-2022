package by.kalllegar.quizer.tasks.math_tasks;

import by.kalllegar.quizer.Result;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    private final String text;
    private final String answer;

    public ExpressionTask(double firstNumber, double secondNumber, int accuracy, Operation operation) {
        super(firstNumber, secondNumber, accuracy, operation);
        text = "The accuracy of the answer must be at least " + getaccuracyAsDoubleValue() + "\n" +
                firstNumber + Operation.toChar(operation) + secondNumber + "=?";
        if (secondNumber == 0 && operation == Operation.DIVISION) {
            answer = "invalid operation";
            return;
        }
        answer = String.valueOf(calculate(firstNumber, secondNumber, operation));
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        if (answer == null) {
            throw new NullPointerException();
        }
        if (!answer.matches("^-?[0-9]+(\\.[0-9]+)?$") && !answer.equalsIgnoreCase("invalid operation")) {
            return Result.INCORRECT_INPUT;
        }
        return checkAnswer(answer, this.answer);
    }

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber минимальное число
         * @param maxNumber максимальное число
         */
        public Generator(double minNumber,
                         double maxNumber,
                         int accuracy,
                         EnumSet<Operation> operations) {
            super(minNumber, maxNumber, accuracy, operations);
        }

        public Generator(double minNumber,
                         double maxNumber,
                         EnumSet<Operation> operations) {
            this(minNumber, maxNumber, 0, operations);
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        public ExpressionTask generate() {
            return new ExpressionTask(getRandomNumberForTask(), getRandomNumberForTask(),
                    getaccuracy(), getRandomOperationFromSet());
        }
    }
}
