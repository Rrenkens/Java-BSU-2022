package by.toharrius.quizer.tasks.math_tasks;

import by.toharrius.quizer.CopyParameter;
import by.toharrius.quizer.Result;
import by.toharrius.quizer.Task;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTask extends AbstractMathTask {
    private final boolean variableLeftSide;
    public EquationTask(double a, MathOperation op, double b, boolean variableLeftSide) {
        super(a, op, b);
        this.variableLeftSide = variableLeftSide;
    }
    @Override
    public String getText() {
        return "Решай уравнение: " +
                (variableLeftSide
                        ? "x " + op + " " + b
                        : a + " " + op + " x"
                ) + " = " + c;
    }

    @Override
    public Result validate(String answer) {
        double value;
        try {
            value = Integer.parseInt(answer);
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
        var correct = variableLeftSide ? a : b;
        return value == correct ? Result.OK : Result.WRONG;
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber,
                         EnumSet<MathOperation> allowed,
                         int precision) {
            super(minNumber, maxNumber, allowed, precision);
        }
        public Generator(double minNumber, double maxNumber, EnumSet<MathOperation> allowed) {
            this(minNumber, maxNumber, allowed, 0);
        }
        public Generator(Generator other, CopyParameter f) {
            this(other.getMinNumber(), other.getMaxNumber(), other.getAllowed());
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
