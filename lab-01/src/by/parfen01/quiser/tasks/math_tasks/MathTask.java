package by.parfen01.quiser.tasks.math_tasks;

import by.parfen01.quiser.Task;

import java.security.InvalidParameterException;

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
}
