package by.parfen01.quiser.tasks.math_tasks;

import by.parfen01.quiser.Result;

import java.security.InvalidParameterException;
import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    protected final double firstNumber;
    protected final double secondNumber;
    protected final Operation operation;
    protected final int precision;

    public AbstractMathTask(double firstNumber, double secondNumber, int precision, Operation operation) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.precision = precision;
        this.operation = operation;
    }

    public int getPrecision() {
        return precision;
    }

    public double getPrecisionAsDoubleValue() {
        if (operation == Operation.ADDITION || operation == Operation.SUBTRACTION) {
            return Math.pow(10, -getPrecision() - 1);
        } else {
            return Math.pow(10, -2 * getPrecision());
        }
    }

    public boolean doubleEqual(double first, double second) {
            return Math.abs(first - second) < getPrecisionAsDoubleValue();
    }

    public Result checkAnswer(String answer, String providedAnswer) {
        if (answer.equals(providedAnswer)) {
            return Result.OK;
        } else {
            double doubleAnswer = Double.parseDouble(answer);
            double doubleProvidedAnswer = Double.parseDouble(providedAnswer);
            if (doubleEqual(doubleAnswer, doubleProvidedAnswer)) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        }
    }

    public abstract static class Generator implements  MathTask.Generator {
        protected final double minNumber;
        protected final double maxNumber;

        protected final int precision;
        protected final EnumSet<Operation> operations;

        public Generator(double minNumber, double maxNumber, EnumSet<MathTask.Operation> operations) {
            this(minNumber, maxNumber, 0, operations);
        }

        public Generator(double minNumber, double maxNumber, int precision, EnumSet<MathTask.Operation> operations) {
            this.precision = precision;
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
        public int getPrecision() {
            return precision;
        }

        @Override
        public EnumSet<MathTask.Operation> getOperations() {
            return operations;
        }
    }
}
