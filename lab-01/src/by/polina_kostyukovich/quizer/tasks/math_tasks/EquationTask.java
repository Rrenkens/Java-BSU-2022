package by.polina_kostyukovich.quizer.tasks.math_tasks;

import by.polina_kostyukovich.quizer.Result;
import by.polina_kostyukovich.quizer.exceptions.BadGeneratorsException;
import by.polina_kostyukovich.quizer.tasks.Task;

import java.util.EnumSet;
import java.util.Random;

public class EquationTask extends AbstractMathTask {
    private final char operator;
    private final int answer;
    private final boolean isXOnFirstPosition;
    private final boolean eachAnswerIsCorrect;

    public EquationTask(int number1, int number2, Operation operation, boolean isXOnFirstPosition) {
        super(number1, number2);
        if (operation == null) {
            throw new IllegalArgumentException("Operation is null");
        }
        if (number1 == 0 && operation == Operation.DIVISION && isXOnFirstPosition) {
            throw new IllegalArgumentException("The equation is \"x / 0 = a\"");
        }
        operator = getOperator(operation);
        answer = getAnswer(number1, number2, operation, isXOnFirstPosition);
        this.isXOnFirstPosition = isXOnFirstPosition;
        eachAnswerIsCorrect = (number1 == 0 && number2 == 0 &&
                (operation == Operation.DIVISION || operation == Operation.MULTIPLICATION));
    }

    @Override
    public String getText() {
        if (isXOnFirstPosition) {
            return "x " + operator + " " + number1 + " = " + number2 + "\nx = ";
        } else {
            return number1 + " " + operator + " x = " + number2 + "\nx = ";
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
            if (eachAnswerIsCorrect) {
                if (operator == '/' && answerNumber == 0) {
                    return Result.WRONG;
                }
                return Result.OK;
            }
            return answerNumber == this.answer ? Result.OK : Result.WRONG;
        } catch (NumberFormatException exception) {
            return Result.INCORRECT_INPUT;
        }
    }

    private static int getAnswer(int number1, int number2, Operation operation, boolean isXOnFirstPosition) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation is null");
        }
        if (number1 == 0 && number2 == 0 && (operation == Operation.DIVISION
                || operation == Operation.MULTIPLICATION)) {
            return 0;
        }
        switch (operation) {
            case SUM -> {
                return number2 - number1;
            }
            case DIFFERENCE -> {
                return isXOnFirstPosition ? number1 + number2 : number1 - number2;
            }
            case MULTIPLICATION -> {
                return number2 / number1;
            }
            case DIVISION -> {
                return isXOnFirstPosition ? number1 * number2 : number1 / number2;
            }
            default -> {
                return 0;
            }
        }
    }

    public static class Generator extends AbstractMathTask.Generator {
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param operations             EnumSet с разрешенными операциями
         */
        public Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }

        /**
         * return задание типа {@link EquationTask}
         */
        @Override
        public Task generate() {
            Random random = new Random();
            boolean isXOnFirstPosition = random.nextBoolean();

            int number1 = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            int number2 = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            int numberOfOperation = (int) (Math.random() * operations.size());
            Operation operation = (Operation) operations.toArray()[numberOfOperation];

            if ((operation == Operation.DIVISION || operation == Operation.MULTIPLICATION)
                    && (number1 == 0 || number2 == 0)) {
                number1 = 0;
                number2 = 0;
                if (operation == Operation.DIVISION) {
                    isXOnFirstPosition = false;
                }
                return new EquationTask(number1, number2, operation, isXOnFirstPosition);
            }

            if (operation == Operation.MULTIPLICATION) {
                number1 = getDivisor(number2, number1, minNumber);
            }
            if (operation == Operation.DIVISION && !isXOnFirstPosition) {
                number2 = getDivisor(number1, number2, minNumber);
            }

            return new EquationTask(number1, number2, operation, isXOnFirstPosition);
        }
    }
}
