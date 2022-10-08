package by.parfen01.quiser.tasks.math_tasks;

import java.security.InvalidParameterException;
import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    private final int firstNumber;
    private final int secondNumber;
    private final Operation operation;

    AbstractMathTask(int firstNumber, int secondNumber, Operation operation) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operation = operation;
    }

    public abstract static class Generator implements  MathTask.Generator {
        private final int minNumber;
        private final int maxNumber;
        private final EnumSet<Operation> operations;

        protected Generator(int minNumber, int maxNumber, EnumSet<MathTask.Operation> operations) {
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
}
