package by.AlexAzyavchikov.quizer.tasks.math_tasks;

import by.AlexAzyavchikov.quizer.tasks.Task;

public interface MathTask extends Task {

    enum Operator {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION
    }

    static double MakeOperation(double number1, Operator operator, double number2) {
        double result = 0;
        switch (operator) {
            case SUM -> {
                result = number1 + number2;
            }
            case DIFFERENCE -> {
                result = number1 - number2;
            }
            case MULTIPLICATION -> {
                result = number1 * number2;
            }
            case DIVISION -> {
                assert number2 != 0;
                result = number1 / number2;
            }
        }
        return result;
    }

    static double MakeReverseOperation(double number1, Operator operator, double number2) {
        double result = 0;
        switch (operator) {
            case SUM -> {
                result = number1 - number2;
            }
            case DIFFERENCE -> {
                result = number1 + number2;
            }
            case MULTIPLICATION -> {
                result = number1 / number2;
            }
            case DIVISION -> {
                assert number2 != 0;
                result = number1 * number2;
            }
        }
        return result;
    }

    static char OperatorsSymbol(Operator operator) {
        char result = '?';
        switch (operator) {
            case SUM -> {
                result = '+';
            }
            case DIFFERENCE -> {
                result = '-';
            }
            case MULTIPLICATION -> {
                result = '*';
            }
            case DIVISION -> {
                result = '/';
            }
        }
        return result;
    }

    static String ValueInBraces(double value) {
        return "(" + value + ")";
    }
}
