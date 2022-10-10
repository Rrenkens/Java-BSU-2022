package by.polina_kostyukovich.quizer.tasks.math_tasks;

import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    protected final int number1;
    protected final int number2;

    public AbstractMathTask(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    protected static char getOperator(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Operation is null");
        }
        switch (operation) {
            case SUM -> {
                return '+';
            }
            case DIFFERENCE -> {
                return '-';
            }
            case MULTIPLICATION -> {
                return '*';
            }
            case DIVISION -> {
                return '/';
            }
            default -> {
                return '?';
            }
        }
    }

    public static abstract class Generator implements MathTask.Generator {
        protected final int minNumber;
        protected final int maxNumber;
        protected final EnumSet<Operation> operations;

        public Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
            if (operations == null) {
                throw new IllegalArgumentException("EnumSet of possible operations is null");
            }
            if (operations.isEmpty()) {
                throw new IllegalArgumentException("EnumSet of possible operations is empty");
            }
            if (maxNumber < minNumber) {
                throw new IllegalArgumentException("maxNumber is less than minNumber");
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = operations;
        }

        @Override
        public int getMinNumber() {
            return minNumber;
        }

        @Override
        public int getMaxNumber() {
            return maxNumber;
        }
    }
}
