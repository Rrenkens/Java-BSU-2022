package by.toharrius.quizer.tasks.math_tasks;

import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    protected final int a;
    protected final MathOperation op;
    protected final int b;
    protected final int c;
    public AbstractMathTask(int a, MathOperation op, int b) {
        this.a = a;
        this.op = op;
        this.b = b;
        this.c = op.eval(a, b);
    }

    protected abstract static class Generator implements MathTask.Generator {
        private final int minNumber;
        private final int maxNumber;
        private final EnumSet<MathOperation> allowed;
        public Generator(int minNumber, int maxNumber,
                         EnumSet<MathOperation> allowed) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.allowed = allowed;
        }

        @Override
        public int getMinNumber() {
            return minNumber;
        }

        @Override
        public int getMaxNumber() {
            return maxNumber;
        }

        @Override
        public EnumSet<MathOperation> getAllowed() {
            return allowed;
        }
    }
}
