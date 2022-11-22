package by.toharrius.quizer.task_generators;

import by.toharrius.quizer.MathOperation;
import by.toharrius.quizer.Task;
import by.toharrius.quizer.TaskGenerator;
import by.toharrius.quizer.tasks.EquationTask;
import by.toharrius.quizer.tasks.ExpressionTask;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class EquationTaskGenerator implements TaskGenerator {
    /**
     * @param minNumber              минимальное число
     * @param maxNumber              максимальное число
     * @param generateSum            разрешить генерацию с оператором +
     * @param generateDifference     разрешить генерацию с оператором -
     * @param generateMultiplication разрешить генерацию с оператором *
     * @param generateDivision       разрешить генерацию с оператором /
     */
    private final int least;
    private final int bound;
    private final EnumSet<MathOperation> allowed;

    EquationTaskGenerator(
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

    /**
     * return задание типа {@link EquationTask}
     */
    @Override
    public EquationTask generate() {
        var r = ThreadLocalRandom.current();
        int a = r.nextInt(least, bound);
        int b = r.nextInt(least, bound);
        var stream = allowed.stream();
        var index = r.nextLong(stream.count());
        var op = stream.skip(index).findFirst().get();
        var ans = op.eval(a, b);
        return r.nextBoolean()
            ? new EquationTask(a, op, ans, b)
            : new EquationTask(op, a, ans, b);
    }
}
