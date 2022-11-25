package by.parfen01.quiser.tasks.math_tasks;

import by.parfen01.quiser.Result;
import by.parfen01.quiser.Task;

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

    static boolean doubleEqual(double first, double second) {
        final double K_EPS = 0.001;
        return Math.abs(first - second) <= K_EPS;
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
            case DIVISION ->  {
                return firstValue / secondValue;
            }
        }

        throw new InvalidParameterException();
    }

    static Result checkAnswer(String answer, String providedAnswer) {
        if (answer.equals(providedAnswer)) {
            return Result.OK;
        } else {
            double doubleAnswer = Double.parseDouble(answer);
            double doubleProvidedAnswer = Double.parseDouble(providedAnswer);
            if (MathTask.doubleEqual(doubleAnswer, doubleProvidedAnswer)) {
                return Result.OK;
            } else {
                return Result.WRONG;
            }
        }
    }

    interface Generator extends Task.Generator {
        double getMinNumber(); // получить минимальное число
        double getMaxNumber(); // получить максимальное число
        EnumSet<Operation> getOperations();
        /**
         * @return разница между максимальным и минимальным возможным числом
         */
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }

        default double getRandomNumberForTask() {
            double result = Math.random() * getDiffNumber() + getMinNumber();
            return (long) (result * 1000) / 1000.0;
        }

        default MathTask.Operation getRandomOperationFromSet() {
                int pos = (int)(Math.random() * getOperations().size());
                return getOperations()
                        .stream()
                        .skip(pos)
                        .limit(1)
                        .findFirst()
                        .orElse(Operation.ADDITION);
        }
    }
}
