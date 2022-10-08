package by.parfen01.quiser.tasks.math_tasks;

import by.parfen01.quiser.Result;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    private final String text;
    private final String answer;

    public ExpressionTask(int firstNumber, int secondNumber, Operation operation) {
        super(firstNumber, secondNumber, operation);
        text = firstNumber + Operation.toChar(operation) + String.valueOf(secondNumber) + "=?";
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
        if (!answer.matches("^-?[0-9]+$") && !answer.equalsIgnoreCase("invalid operation")) {
            return Result.INCORRECT_INPUT;
        }
        if (answer.equals("-0") && this.answer.equals("0")) {
            return Result.OK;
        }
        return this.answer.equals(answer) ? Result.OK : Result.WRONG;
    }

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         */
        public Generator(int minNumber,
                                       int maxNumber,
                                       EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        public ExpressionTask generate() {
            return new ExpressionTask(getRandomNumberForTask(), getRandomNumberForTask(), getRandomOperationFromSet());
        }
    }
}
