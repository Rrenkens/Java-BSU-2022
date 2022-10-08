package by.parfen01.quiser.tasks.math_tasks;

import by.parfen01.quiser.Task;

import java.security.InvalidParameterException;
import java.util.EnumSet;

public interface MathTask extends Task {
    enum Operation {
        ADDITION, // Операция сложения(+)
        SUBTRACTION, // Операция вычитания(-)
        MULTIPLICATION, // Операция умножения(*)
        DIVISION; // Операция деления(/)

        public static Operation fromInt(int number) {
            switch (number) {
                case 0 -> {
                    return ADDITION;
                }
                case 1 -> {
                    return SUBTRACTION;
                }
                case 2 -> {
                    return MULTIPLICATION;
                }
                case 3 -> {
                    return DIVISION;
                }
            }

            throw new InvalidParameterException();
        }

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
                case DIVISION ->  {
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
                case DIVISION ->  {
                    return Operation.MULTIPLICATION;
                }
            }

            throw new InvalidParameterException();
        }
    }

    default int calculate(int firstValue, int secondValue, Operation operation) {
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
            case DIVISION ->  {
                return firstValue / secondValue;
            }
        }

        throw new InvalidParameterException();
    }

    interface Generator extends Task.Generator {
        int getMinNumber(); // получить минимальное число
        int getMaxNumber(); // получить максимальное число
        EnumSet<Operation> getOperations();
        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default int getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }

        default int getRandomNumberForTask() {
            return (int)(Math.random() * getDiffNumber() + getMinNumber());
        }

        default MathTask.Operation getRandomOperationFromSet() {
            while (true) {
                MathTask.Operation operationToUse = MathTask.Operation.fromInt((int)(Math.random() * 4));
                if (getOperations().contains(operationToUse)) {
                    return operationToUse;
                }
            }
        }
    }
}
