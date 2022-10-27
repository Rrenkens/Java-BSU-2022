package by.polina_kostyukovich.quizer.tasks.math_tasks;

import by.polina_kostyukovich.quizer.Result;
import by.polina_kostyukovich.quizer.exceptions.BadGeneratorException;
import by.polina_kostyukovich.quizer.exceptions.BadTaskException;
import by.polina_kostyukovich.quizer.tasks.Task;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    private final char operator;
    private final int answer;

    public ExpressionTask(int number1, int number2, Operation operation) {
        super(number1, number2);
        if (operation == null) {
            throw new IllegalArgumentException("Operation is null");
        }
        if (number2 == 0 && operation == Operation.DIVISION) {
            throw new IllegalArgumentException("The expression is \"a / 0 = b\"");
        }
        if (operation == Operation.DIVISION && number1 % number2 != 0) {
            throw new BadTaskException("Not integer division");
        }

        operator = getOperator(operation);
        answer = getAnswer(number1, number2, operation);
    }

    @Override
    public String getText() {
        if (number2 < 0) {
            return number1 + " " + operator + " (" + number2 + ") = ";
        } else {
            return number1 + " " + operator + " " + number2 + " = ";
        }
    }

    @Override
    public String getAnswer() {
        return String.valueOf(answer);
    }

    @Override
    public Result validate(String answer) {
        try {
            int answerNumber = Integer.parseInt(answer);
            return answerNumber == this.answer ? Result.OK : Result.WRONG;
        } catch (NumberFormatException exception) {
            return Result.INCORRECT_INPUT;
        }
    }

    private static int getAnswer(int number1, int number2, Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation is null");
        }
        switch (operation) {
            case SUM -> {
                return number1 + number2;
            }
            case DIFFERENCE -> {
                return number1 - number2;
            }
            case MULTIPLICATION -> {
                return number1 * number2;
            }
            case DIVISION -> {
                return number1 / number2;
            }
            default -> throw new BadTaskException("Invalid operation");
        }
    }

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber  минимальное число
         * @param maxNumber  максимальное число
         * @param operations EnumSet с разрешенными операциями
         */
        public Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }

        /**
         * return задание типа {@link ExpressionTask}
         */
        @Override
        public Task generate() {
            if (maxNumber == 0 && minNumber == 0 && operations.size() == 1 &&
                    operations.toArray()[0] == Operation.DIVISION) {
                throw new BadGeneratorException("Generator allows only \"0 / 0 = ?\" expression");
            }
            int number1 = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            int number2 = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            int numberOfOperation = (int) (Math.random() * operations.size());
            Operation operation = (Operation) operations.toArray()[numberOfOperation];
            if (operation == Operation.DIVISION && maxNumber == 0 && minNumber == 0) {
                operation = (Operation) operations.toArray()[(numberOfOperation + 1) % operations.size()];
            }
            if (operation == Operation.DIVISION) {
                number2 = getDivisor(number1, number2, minNumber);
            }
            return new ExpressionTask(number1, number2, operation);
        }
    }
}