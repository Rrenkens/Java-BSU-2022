package by.ZaharKalosha.quizzer.tasks.math_tasks;

import by.ZaharKalosha.quizzer.Result;

import java.security.InvalidParameterException;
import java.util.EnumSet;

public abstract class AbstractMathTask implements MathTask {
    protected final double firstNumber;
    protected final double secondNumber;
    protected Operation operation;
    protected final int precision;

    public AbstractMathTask(double firstNumber, double secondNumber, Operation operation, int precision) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operation = operation;
        this.precision = precision;
    }

    public int getPrecision() {
        return precision;
    }

    public double getDoublePrecision() {
        if (operation == Operation.ADDITION || operation == Operation.SUBTRACTION) {
            return Math.pow(10, -(getPrecision() + 1));
        } else {
            return Math.pow(10, -getPrecision());
        }
    }

    static char getCharOperator(Operation operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Оператор в данный момент равен null");
        }

        switch (operation) {
            case ADDITION -> {
                return '+';
            }
            case SUBTRACTION -> {
                return '-';
            }
            case MULTIPLICATION -> {
                return '*';
            }
            case DIVISION -> {
                return '/';
            }
            default -> throw new IllegalArgumentException("Некорректное действие");
        }
    }

    double calculate(double firstNumber, double secondNumber, Operation operation) {
        switch (operation) {
            case ADDITION -> {
                return firstNumber + secondNumber;
            }
            case SUBTRACTION -> {
                return firstNumber - secondNumber;
            }
            case MULTIPLICATION -> {
                return firstNumber * secondNumber;
            }
            case DIVISION -> {
                return firstNumber / secondNumber;
            }
        }
        throw new InvalidParameterException();
    }

    public Result checkAnswer(String answer, String providedAnswer) {
        if (doubleEqual(Double.parseDouble(answer), Double.parseDouble(providedAnswer))) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }

    public boolean doubleEqual(double firstNumber, double secondNumber) {
        return Math.abs(firstNumber - secondNumber) < getDoublePrecision();
    }

    public abstract static class Generator implements MathTask.Generator {
        protected final double minNumber;
        protected final double maxNumber;

        protected final int precision;
        protected final EnumSet<Operation> operations;

        public Generator(double minNumber, double maxNumber, EnumSet<MathTask.Operation> operations) {
            this(minNumber, maxNumber, 0, operations);
        }

        public Generator(double minNumber, double maxNumber, int precision, EnumSet<MathTask.Operation> operations) {
            if (operations.equals(EnumSet.noneOf(MathTask.Operation.class))) {
                throw new InvalidParameterException();
            }

            if (maxNumber < minNumber) {
                throw new IllegalArgumentException();
            }
            this.precision = precision;
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = operations.clone();
        }

        double getRandomNumberForTask() {
            return (Math.random() * (getDiffNumber() + 1) + minNumber);
        }

        Operation getRandomOperation() {
            int numberOfOperation = (int) (Math.random() * operations.size());
            return (Operation) operations.toArray()[numberOfOperation];
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
