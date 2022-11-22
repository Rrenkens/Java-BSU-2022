package by.toharrius.quizer.tasks;

import by.toharrius.quizer.MathOperation;
import by.toharrius.quizer.Result;
import by.toharrius.quizer.Task;
import by.toharrius.quizer.TaskGenerator;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class ExpressionTask implements Task {
    private final int a;
    private final MathOperation operation;
    private final int b;
    private final int result;

    public ExpressionTask(int a, MathOperation operation, int b) {
        this.a = a;
        this.operation = operation;
        this.b = b;
        this.result = operation.eval(a, b);
    }

    @Override
    public String getText() {
        return String.valueOf(a) + ' ' + operation + ' ' + b + " = ";
    }

    @Override
    public Result validate(String answer) {
        try {
            if (Integer.parseInt(answer) == result) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }

    public static class Generator implements TaskGenerator {
        private final int least;
        private final int bound;
        private final EnumSet<MathOperation> allowed;
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         * @param generateSum            разрешить генерацию с оператором +
         * @param generateDifference     разрешить генерацию с оператором -
         * @param generateMultiplication разрешить генерацию с оператором *
         * @param generateDivision       разрешить генерацию с оператором /
         */
        Generator(
                int minNumber,
                int maxNumber,
                boolean generateSum,
                boolean generateDifference,
                boolean generateMultiplication,
                boolean generateDivision
        ) {
            least = minNumber;
            bound = maxNumber + 1;
            allowed = EnumSet.noneOf(MathOperation.class);
            if (generateSum) allowed.add(MathOperation.ADD);
            if (generateDifference) allowed.add(MathOperation.SUBTRACT);
            if (generateMultiplication) allowed.add(MathOperation.MULTIPLY);
            if (generateDivision) allowed.add(MathOperation.DIVIDE);
        }
        @Override
        public ExpressionTask generate() {
            var r = ThreadLocalRandom.current();
            int a = r.nextInt(least, bound);
            int b = r.nextInt(least, bound);
            var stream = allowed.stream();
            var index = r.nextLong(stream.count());
            var op = stream.skip(index).findFirst().get();
            return new ExpressionTask(a, op, b);
        }
    }
}
