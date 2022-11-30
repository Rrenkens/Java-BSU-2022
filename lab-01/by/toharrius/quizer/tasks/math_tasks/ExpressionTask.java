package by.toharrius.quizer.tasks.math_tasks;

import by.toharrius.quizer.CopyParameter;
import by.toharrius.quizer.Result;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    public ExpressionTask(double a, MathOperation op, double b) {
        super(a, op, b);
    }

    @Override
    public String getText() {
        return "Решай пример: " + a + ' ' + op + ' ' + b + " = ?";
    }

    @Override
    public Result validate(String answer) {
        try {
            if (Integer.parseInt(answer) == c) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
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
        public Generator(EquationTask.Generator other, CopyParameter f) {
            this(other.getMinNumber(), other.getMaxNumber(), other.getAllowed());
        }

        @Override
        public ExpressionTask generate() {
            return new ExpressionTask(
                    generateOperand(),
                    generateMathOperation(),
                    generateOperand()
            );
        }
    }
}
