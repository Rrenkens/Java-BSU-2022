package by.ZaharKalosha.quizzer.tasks.math_tasks;

import by.ZaharKalosha.quizzer.Result;

import java.security.InvalidParameterException;
import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {
    private String text;
    private String answer;

    public EquationTask(double firstNumber, double secondNumber, int precision, int xPosition, Operation operation) {
        super(firstNumber, secondNumber, operation, precision);

        if (xPosition != 0 && xPosition != 1) {
            throw new IllegalArgumentException();
        }

        String temp;
        if (doubleEqual(firstNumber, 0.0) &&
                doubleEqual(secondNumber, 0.0) &&
                operation == Operation.MULTIPLICATION) {
            temp = "Невозможно посчитать ответ.";
            return;
        }

        String preTaskOutput = "Необходима точность ответа не менее " + getDoublePrecision() + "\n";
        if (xPosition == 0) {
            preTaskOutput += "X" + AbstractMathTask.getCharOperator(operation) + firstNumber + "=" + secondNumber;
        } else {
            preTaskOutput += firstNumber + AbstractMathTask.getCharOperator(operation) + "X" + "=" + secondNumber;
        }

        temp = String.valueOf(calculate(secondNumber, firstNumber, getOppositeOperation(operation)));

        if (xPosition == 1) {
            if (operation == Operation.SUBTRACTION || operation == Operation.DIVISION) {
                temp = String.valueOf(calculate(firstNumber, secondNumber, operation));
            }
        }

        text = preTaskOutput;
        answer = temp;
    }

    static Operation getOppositeOperation(Operation operation) {
        switch (operation) {
            case ADDITION -> {
                return Operation.SUBTRACTION;
            }
            case SUBTRACTION -> {
                return Operation.ADDITION;
            }
            case MULTIPLICATION -> {
                return Operation.DIVISION;
            }
            case DIVISION -> {
                return Operation.MULTIPLICATION;
            }
        }

        throw new InvalidParameterException();
    }

    @Override
    public String getAnswer() {
        return answer;
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

        try {
            double givenAnswer = Double.parseDouble(answer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }

        return checkAnswer(this.answer, answer);
    }

    public static class Generator extends AbstractMathTask.Generator {
        private final int[] possiblePositionsOfX;

        /**
         * @param minNumber минимальное число
         * @param maxNumber максимальное число
         */

        public Generator(double minNumber, double maxNumber, EnumSet<Operation> operations, int[] possiblePositionsOfX) {
            this(minNumber, maxNumber, 0, operations, possiblePositionsOfX);
        }

        public Generator(double minNumber, double maxNumber, int precision,
                         EnumSet<Operation> operations, int[] possiblePositionsOfX) {
            super(minNumber, maxNumber, precision, operations);
            this.possiblePositionsOfX = possiblePositionsOfX.clone();
        }

        /**
         * return задание типа {@link EquationTask}
         */
        public EquationTask generate() {
            int xPos = possiblePositionsOfX[(int) (Math.random() * possiblePositionsOfX.length)];
            return new EquationTask(getRandomNumberForTask(), getRandomNumberForTask(),
                    getPrecision(), xPos, getRandomOperation());
        }
    }
}
