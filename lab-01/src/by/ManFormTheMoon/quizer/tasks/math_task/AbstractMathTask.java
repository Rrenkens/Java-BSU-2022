package by.ManFormTheMoon.quizer.tasks.math_task;

import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    protected final int first;
    protected final int second;

    public AbstractMathTask(int number1, int number2) {
        this.first = number1;
        this.second = number2;
    }

    public static int findAnyDivisor(int x, int minNumber, int maxNumber) {
        if (x == 0) {
            return (minNumber == 0 ? maxNumber : minNumber);
        }
        int maxValue = Math.max(-Math.abs(x), minNumber);
        int value = (int)(Math.random() * (Math.min(Math.abs(x), maxNumber) - maxValue) + (double) maxValue);
        if (value != 0) {
            value = value - x % value;
        } else {
            value = x;
        }
        int arr[] = new int[4];
        arr[0] = value;
        arr[1] = value + x;
        arr[2] = value - x;
        arr[3] = x;
        for (int i = 0; i < 4; i++) {
            if (arr[i] != 0 && minNumber <= arr[i] && arr[i] <= maxNumber && x % arr[i] == 0) {
                return arr[i];
            }
        }
        // TODO : add exception
        return x;
    }

    public static abstract class Generator implements MathTask.Generator {
        protected final int minNumber;
        protected final int maxNumber;
        protected final EnumSet<Operation> operations;

        public Generator(int minNumber, int maxNumber, EnumSet<Operation> operations) {
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
