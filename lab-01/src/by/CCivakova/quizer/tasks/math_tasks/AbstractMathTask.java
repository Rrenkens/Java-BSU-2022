package by.CCivakova.quizer.tasks.math_tasks;

import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask{
    protected final int const1;
    protected final int const2;

    public AbstractMathTask(int const1, int const2) {
        this.const1 = const1;
        this.const2 = const2;
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
