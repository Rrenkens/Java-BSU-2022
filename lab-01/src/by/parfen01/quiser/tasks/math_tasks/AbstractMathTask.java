package by.parfen01.quiser.tasks.math_tasks;

import java.security.InvalidParameterException;
import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    protected final double firstNumber;
    protected final double secondNumber;
    protected final Operation operation;

    public AbstractMathTask(double firstNumber, double secondNumber, Operation operation) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operation = operation;
    }

    public abstract static class Generator implements  MathTask.Generator {
        protected final double minNumber;
        protected final double maxNumber;
        protected final EnumSet<Operation> operations;

        public Generator(double minNumber, double maxNumber, EnumSet<MathTask.Operation> operations) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = operations.clone();
            if (operations.equals(EnumSet.noneOf(MathTask.Operation.class))) {
                throw new InvalidParameterException();
            }
            if (maxNumber < minNumber) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public EnumSet<MathTask.Operation> getOperations() {
            return operations;
        }
    }
}
