package by.polina_kostyukovich.quzer.tasks.math_tasks;

import by.polina_kostyukovich.quzer.tasks.Task;

public abstract class AbstractMathTask implements MathTask {
    protected final int number1;
    protected final int number2;

    public AbstractMathTask(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    protected static char getOperator(Operation operation) {
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
        protected final Operation operation;

        public Generator(int minNumber, int maxNumber, Operation operation) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operation = operation;
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
