package by.parfen01.quiser.task_generators.math_task_generators;

import by.parfen01.quiser.tasks.math_tasks.MathTask;

import java.security.InvalidParameterException;
import java.util.EnumSet;

public abstract class AbstractMathTaskGenerator implements  MathTaskGenerator {
    private final int minNumber;
    private final int maxNumber;
    private final EnumSet<MathTask.Operation> operations;

    AbstractMathTaskGenerator(int minNumber, int maxNumber, EnumSet<MathTask.Operation> operations) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.operations = operations.clone();
        if (operations.equals(EnumSet.allOf(MathTask.Operation.class))) {
            throw new InvalidParameterException();
        }
        if (maxNumber < minNumber) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public EnumSet<MathTask.Operation> getOperations() {
        return operations;
    }
}
