package by.toharrius.quizer.tasks.math_tasks;

import by.toharrius.quizer.Result;

import java.util.EnumSet;

public class ExpressionTask extends AbstractMathTask {
    public ExpressionTask(int a, MathOperation op, int b) {
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
        public Generator(int minNumber, int maxNumber, EnumSet<MathOperation> allowed) {
            super(minNumber, maxNumber, allowed);
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
