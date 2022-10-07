package by.AlexAzyavchikov.quizer.tasks.math_tasks;

import by.AlexAzyavchikov.quizer.tasks.Task;

public interface MathTask extends Task {

    enum Operator {
        SUM,
        DIFFERENCE,
        MULTIPLICATION,
        DIVISION;

        public double makeOperation(double number1, double number2) {
            double result = 0;
            switch (this) {
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
                    result = number1 / number2;
                }
            }
            return result;
        }

        public double makeReverseOperation(double number1, double number2) {
            double result = 0;
            switch (this) {
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
                    result = number1 * number2;
                }
            }
            return result;
        }
        public char symbol() {
            char result = '?';
            switch (this) {
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
    }

    int getPrecision();

    default double Round(double value) {
        double scale = Math.pow(10, getPrecision());
        return Math.round(value * scale) / scale;
    }
}
