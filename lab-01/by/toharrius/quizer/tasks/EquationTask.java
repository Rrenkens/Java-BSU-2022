package by.toharrius.quizer.tasks;

import by.toharrius.quizer.MathOperation;
import by.toharrius.quizer.Result;
import by.toharrius.quizer.Task;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTask extends AbstractMathTask {
    private final boolean variableLeftSide;
    public EquationTask(int a, MathOperation op, int b, boolean variableLeftSide) {
        super(a, op, b);
        this.variableLeftSide = variableLeftSide;
    }
    @Override
    public String getText() {
        return (variableLeftSide
                        ? "x " + op + " " + b
                        : a + " " + op + " x"
                ) + " = " + c;
    }

    @Override
    public Result validate(String answer) {
        int value;
        try {
            value = Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
        var correct = variableLeftSide ? a : b;
        return value == correct ? Result.OK : Result.WRONG;
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(int minNumber, int maxNumber, EnumSet<MathOperation> allowed) {
            super(minNumber, maxNumber, allowed);
        }
        @Override
        public Task generate() {
            return new EquationTask(
                    generateOperand(),
                    generateMathOperation(),
                    generateOperand(),
                    ThreadLocalRandom.current().nextBoolean()
            );
        }
    }
}
