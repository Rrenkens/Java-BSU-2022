package by.CCivakova.quizer.tasks.math_tasks;

import by.CCivakova.quizer.Result;
import by.CCivakova.quizer.tasks.Task;

import java.security.InvalidParameterException;
import java.util.EnumSet;
import java.util.Random;

public class EquationTask extends AbstractMathTask {
    private final Operation operation;
    private final int right_ans;
    private final boolean isFirsVariable;
    public EquationTask(int const1, int const2, boolean isFirsVariable, Operation operation) {
        super(const1, const2);
        if (operation == null) {
            throw new IllegalArgumentException();
        }
        this.operation = operation;
        this.isFirsVariable = isFirsVariable;

        switch (operation) {
            case ADDITION:
                right_ans = const2 - const1;
                return;
            case SUBTRACTION:
                 right_ans = isFirsVariable ? const1 + const2 : const1 - const2;
                 return;
            case MULTIPLICATION:
                right_ans = const2 / const1;
                return;
            case DIVISION:
                right_ans = isFirsVariable ? const1 * const2 : const1 / const2;
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
        if (isFirsVariable) {
            return "Calculate answer\n x " +  sym + " " + const1 + " = " + const2;
        } else {
            return "Calculate answer\n " + const1 + " " + sym + " x = " + const2;
        }
    }

    @Override
    public Result validate(String answer) {
        try {
            return  right_ans == Integer.parseInt(answer) ? Result.OK : Result.WRONG;
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
            boolean isFirsVariable = random.nextBoolean();

            if (operation == Operation.MULTIPLICATION) {
                const2 *= const1;
            }
            return new EquationTask(const1, const2, isFirsVariable, operation);
        }
    }
}
