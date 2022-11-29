package by.CCivakova.quizer.tasks.math_tasks;

import by.CCivakova.quizer.Result;
import by.CCivakova.quizer.tasks.Task;

import java.security.InvalidParameterException;
import java.util.EnumSet;
import java.util.Random;

public class ExpressionTask extends AbstractMathTask{
    private final Operation operation;
    private final int right_answer;
    public ExpressionTask(int const1, int const2, Operation operation) {
        super(const1, const2);
        this.operation = operation;
        switch (operation) {
            case ADDITION:
                right_answer = const1 + const2;
                return;
            case SUBTRACTION:
                right_answer = const1 - const2;
                return;
            case MULTIPLICATION:
                right_answer = const1 * const2;
                return;
            case DIVISION:
                right_answer = const1 / const2;
                return;
        }
        throw new InvalidParameterException();
    }

    @Override
    public String getText() {
        char sym = switch (operation) {
            case ADDITION -> '+';
            case SUBTRACTION -> '-';
            case MULTIPLICATION -> '*';
            default -> '/';
        };
        return "Calculate answer\n" + const1 + " " + sym + " " + const2 + " = ";
    }

    @Override
    public Result validate(String answer) {
        try {
            return Integer.parseInt(answer) == right_answer ? Result.OK : Result.WRONG;
        } catch (NumberFormatException exception) {
            return Result.INCORRECT_INPUT;
        }
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
            super(minNumber, maxNumber, operations);
        }

        @Override
        public Task generate() {
            Random random = new Random();
            Operation operation = Operation.values()[random.nextInt(operations.size())];
            int const1 = (int)(Math.random() * (getDiffNumber() + 1)) + minNumber;

            int const2 = (int)(Math.random() * (getDiffNumber() + 1)) + minNumber;

            if (const1 == 0) {
                const1 = (minNumber == 0 ? maxNumber : minNumber);
            }
            if (const2 == 0) {
                const2 = (minNumber == 0 ? maxNumber : minNumber);
            }

            return new ExpressionTask(const1, const2, operation);
        }
    }
}
