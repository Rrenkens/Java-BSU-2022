package by.kalllegar.quizer.tasks.math_tasks;

import by.kalllegar.quizer.Task;
import by.kalllegar.quizer.task_generators.TaskGenerator;

import java.security.InvalidParameterException;
import java.util.EnumSet;

public interface MathTask extends Task {
    enum Operation {
        ADDITION, // Операция сложения(+)
        SUBTRACTION, // Операция вычитания(-)
        MULTIPLICATION, // Операция умножения(*)
        DIVISION; // Операция деления(/)


        static char toChar(Operation operation) {
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
            }

            throw new InvalidParameterException();
        }

        static Operation getReverseOperation(Operation operation) {
            switch (operation) {
                case ADDITION -> {
                    return Operation.SUBTRACTION;
                }
                case SUBTRACTION -> {
                    return Operation.ADDITION;
                }
                case MULTIPLICATION -> {
                    return Operation.DIVISION;
                }
                case DIVISION -> {
                    return Operation.MULTIPLICATION;
                }
            }

            throw new InvalidParameterException();
        }
    }

    default double calculate(double firstValue, double secondValue, Operation operation) {
        switch (operation) {
            case ADDITION -> {
                return firstValue + secondValue;
            }
            case SUBTRACTION -> {
                return firstValue - secondValue;
            }
            case MULTIPLICATION -> {
                return firstValue * secondValue;
            }
            case DIVISION -> {
                return firstValue / secondValue;
            }
        }

        throw new InvalidParameterException();
    }

    interface Generator extends TaskGenerator {
        double getMinNumber(); // получить минимальное число

        double getMaxNumber(); // получить максимальное число

        int getaccuracy();

        EnumSet<Operation> getOperations();

        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }

        default double getRandomNumberForTask() {
            double result = Math.random() * getDiffNumber() + getMinNumber();
            result = (long) (result * Math.pow(10, getaccuracy())) / Math.pow(10, getaccuracy());
            return result;
        }

        default MathTask.Operation getRandomOperationFromSet() {
            int pos = (int) (Math.random() * getOperations().size());
            return getOperations()
                    .stream()
                    .skip(pos)
                    .limit(1)
                    .findFirst()
                    .orElse(Operation.ADDITION);
        }
    }
}
