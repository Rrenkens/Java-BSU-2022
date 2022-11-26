package by.ManFormTheMoon.quizer.tasks.math_task;

import by.ManFormTheMoon.quizer.Result;
import by.ManFormTheMoon.quizer.tasks.Task;

import java.util.EnumSet;
import java.util.Random;

public class EquationTask extends AbstractMathTask {

    private final Operation operation;
    private final int ans;
    private final boolean isFirst;
    private final boolean isAnyValid;
    public EquationTask(int first_, int second_, boolean isFirst_, Operation operation_) {
        super(first_, second_);
        operation = operation_;
        isFirst = isFirst_;
        ans = calcAns(first_, second_, operation, isFirst_);
        if ((operation == Operation.MULTIPLY || operation == Operation.DIVIDE) && first_ == 0 && second_ == 0) {
            isAnyValid = true;
        } else {
            isAnyValid = false;
        }
    }

    @Override
    public String getText() {
        if (isFirst) {
            return "x " + Operation.toChar(operation) + " " + first + " = " + second;
        } else {
            return first + " " + Operation.toChar(operation) + " x = " + second;
        }
    }

    @Override
    public Result validate(String answer) {
        try {
            int userAns = Integer.parseInt(answer);
            if (isAnyValid) {
                if (operation == Operation.DIVIDE && userAns == 0) {
                    return Result.WRONG;
                } else {
                    return Result.OK;
                }
            } else {
                return ans == userAns ? Result.OK : Result.WRONG;
            }
        } catch (NumberFormatException exception) {
            return Result.INCORRECT_INPUT;
        }
    }

    private static int calcAns(int x, int y, Operation operation, boolean isFirst) {
        if (x == 0 && y == 0 && (operation == Operation.DIVIDE
                || operation == Operation.MULTIPLY)) {
            return 1;
        }
        switch (operation) {
            case PLUS:
                return y - x;
            case MINUS:
                return isFirst ? x + y : x - y;
            case MULTIPLY:
                return y / x;
            case DIVIDE:
                return isFirst ? x * y : x / y;
        }

        // TODO : add exception

        return 1;
    }


    public static class Generator extends AbstractMathTask.Generator {

        public Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }


        @Override
        public Task generate() {
            Random random = new Random();
            boolean isFirst = random.nextBoolean();
            Operation operation = Operation.fromInt((int)(Math.random() * operations.size()));
            int first = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            int second = (int) (Math.random() * (getDiffNumber() + 1) + minNumber);
            if ((operation == Operation.DIVIDE || operation == Operation.MULTIPLY)
                    && (first == 0 || second == 0)) {
                return new EquationTask(0, 0, false, operation);
            }
            if (operation == Operation.MULTIPLY) {
                first = findAnyDivisor(second, minNumber, maxNumber);
            } else if (operation == Operation.DIVIDE && !isFirst) {
                second = findAnyDivisor(first, minNumber, maxNumber);
            }
            return new EquationTask(first, second, isFirst, operation);
        }
    }
}
