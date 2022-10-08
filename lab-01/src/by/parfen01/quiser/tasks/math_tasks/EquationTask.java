package by.parfen01.quiser.tasks.math_tasks;

import by.parfen01.quiser.Result;

import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {
    int positionOfX;
    String text;
    String answer;
    public EquationTask(int firstNumber, int secondNumber, int positionOfX, Operation operation) {
        super(firstNumber, secondNumber, operation);
        this.positionOfX = positionOfX;
        if (positionOfX != 0 && positionOfX != 1) {
            throw new IllegalArgumentException();
        }
        if (positionOfX == 0) {
            text = "X" + Operation.toChar(operation) + firstNumber + "=" + secondNumber;
            if (firstNumber == 0 && operation == Operation.DIVISION) {
                answer = "invalid operation";
                return;
            }
            if (firstNumber == 0 && secondNumber != 0 && operation == Operation.MULTIPLICATION) {
                answer = "no right answer";
                return;
            }
            answer = String.valueOf(calculate(secondNumber, firstNumber, Operation.getReverseOperation(operation)));
        } else {
            text = String.valueOf(firstNumber) + Operation.toChar(operation) + "X" + "=" + secondNumber;
            if (firstNumber == 0 && secondNumber != 0 &&
                    (operation == Operation.MULTIPLICATION || operation == Operation.DIVISION)) {
                answer = "no right answer";
                return;
            }
            if (operation == Operation.ADDITION || operation == Operation.MULTIPLICATION) {
                answer = String.valueOf(calculate(secondNumber, firstNumber, Operation.getReverseOperation(operation)));
            } else {
                answer = String.valueOf(calculate(firstNumber, secondNumber, operation));
            }
        }
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
        if (!answer.matches("^-?[0-9]+$") &&
                !answer.equalsIgnoreCase("invalid operation") &&
                !answer.equalsIgnoreCase("no right answer")) {
            return Result.INCORRECT_INPUT;
        }
        if (answer.equals("-0") && this.answer.equals("0")) {
            return Result.OK;
        }
        return this.answer.equals(answer) ? Result.OK : Result.WRONG;
    }

    public static class Generator extends AbstractMathTask.Generator {
        private final int[] possiblePositionsOfX;
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         */
        public Generator(int minNumber,
                                     int maxNumber,
                                     EnumSet<Operation> operations,
                                     int[] possiblePositionsOfX) {
            super(minNumber, maxNumber, operations);
            this.possiblePositionsOfX = possiblePositionsOfX.clone();
        }

        /**
         * return задание типа {@link EquationTask}
         */
        public EquationTask generate() {
            int xPos = possiblePositionsOfX[(int)(Math.random() * possiblePositionsOfX.length)];
            return new EquationTask(getRandomNumberForTask(), getRandomNumberForTask(), xPos, getRandomOperationFromSet());
        }
    }
}
