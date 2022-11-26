package by.ManFormTheMoon.quizer.tasks.math_task;

import by.ManFormTheMoon.quizer.Result;
import by.ManFormTheMoon.quizer.tasks.Task;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    private final Operation operation;
    private final int ans;
    public ExpressionTask(int first_, int second_, Operation operation_) {
        super(first_, second_);
        operation = operation_;
        ans = calcAns(first_, second_, operation_);
    }

    @Override
    public String getText() {
        return first + " " + Operation.toChar(operation) + " " + second + " = ";
    }

    @Override
    public Result validate(String answer) {
        try {
            return Integer.parseInt(answer) == ans ? Result.OK : Result.WRONG;
        } catch (NumberFormatException exception) {
            return Result.INCORRECT_INPUT;
        }
    }

    private int calcAns(int x, int y, Operation operation) {
        switch (operation) {
            case PLUS:
                return x + y;
            case MINUS:
                return x - y;
            case DIVIDE:
                return x / y;
            case MULTIPLY:
                return x * y;
        }
        // TODO : add exception
        return x + y;
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }

        @Override
        public Task generate() {
            Operation operation = Operation.fromInt((int)(Math.random() * operations.size()));
            operation = Operation.DIVIDE;
            int first = (int)(Math.random() * (getDiffNumber() + 1)) + minNumber;
            first = 8;
            if (minNumber == 0 && maxNumber == 0) {
                while (operation == Operation.DIVIDE) {
                    operation = Operation.fromInt((int)(Math.random() * operations.size()));
                }
            }
            int second;
            if (operation != Operation.DIVIDE) {
                second = (int)(Math.random() * (getDiffNumber() + 1)) + minNumber;
            } else {
                second = findAnyDivisor(first, minNumber, maxNumber);
            }

            return new ExpressionTask(first, second, operation);
        }
    }
}
