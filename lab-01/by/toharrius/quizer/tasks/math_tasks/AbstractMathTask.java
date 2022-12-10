package by.toharrius.quizer.tasks.math_tasks;

import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    protected final double a;
    protected final MathOperation op;
    protected final double b;
    protected final double c;
    public AbstractMathTask(double a, MathOperation op, double b) {
        this.a = a;
        this.op = op;
        this.b = b;
        this.c = op.eval(a, b);
    }

    protected abstract static class Generator implements MathTask.Generator {
        private final double minNumber;
        private final double maxNumber;
        private final EnumSet<MathOperation> allowed;
        private final double roundingCoefficient;
        public Generator(double minNumber, double maxNumber,
                         EnumSet<MathOperation> allowed,
                         int precision) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.allowed = allowed;
            this.roundingCoefficient = Math.pow(10, precision);
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        @Override
        public double getRoundingCoefficient() {
            return roundingCoefficient;
        }

        @Override
        public EnumSet<MathOperation> getAllowed() {
            return allowed;
        }
    }
}
